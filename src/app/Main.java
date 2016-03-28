package app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Action;
import model.Event;
import model.ShanXunManager;
import model.bean.IpConfig;
import pane.RouterPane;
import pane.ShanXunSettingPane;
import utils.TextUtil;

public class Main extends Application implements Event {
    Tab dialTab;
    Tab routerTab;
    BorderPane borderPane;
    TabPane tabPane;
    Text actiontarget;
    IpConfig ipConfig;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initView(primaryStage);
    }

    private void initView(Stage primaryStage) {
        primaryStage.setTitle("闪讯wifi助手");
        borderPane = new BorderPane();
        borderPane.setTop(getTab());
        setFootView();
        borderPane.setBottom(actiontarget);
        Scene scene = new Scene(borderPane, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setFootView() {
        actiontarget = new Text();
        actiontarget.setText("未使用");
        actiontarget.setCache(true);
        actiontarget.setFill(Color.RED);
    }

    private TabPane getTab() {
        tabPane = new TabPane();
        dialTab = new Tab("拨号");
        dialTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == true) {
                borderPane.setCenter(ShanXunSettingPane.instance(Main.this));
            }
        });
        dialTab.setClosable(false);
        tabPane.getTabs().add(dialTab);
        routerTab = new Tab("路由器配置");
        routerTab.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == true) {
                borderPane.setCenter(RouterPane.instance(Main.this));
            }
        });
        routerTab.setClosable(false);
        tabPane.getTabs().add(routerTab);
        return tabPane;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void action(Action action) {
        switch (action) {
            case CHECKROUTER:
                setFootView("路由器配置有误，请检查");
                tabPane.getSelectionModel().select(routerTab);
                break;
            case SAVEROUTER:
                setFootView("已保存");
                tabPane.getSelectionModel().select(dialTab);
                break;
            case DIAL:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doDial();
                    }
                }).start();
                break;
            case SENDHEART:
                break;
        }
    }

    private void doDial() {
        setFootView("正在拨号");
        if (ShanXunManager.doDial()) {
            setFootView("正在检查网络");
            String json = ShanXunManager.getIp();
            if (TextUtil.isEmpty(json)) {
                setFootView("拨号失败，请重试");
            } else {
                Gson gson = new Gson();
                ipConfig = gson.fromJson(json, IpConfig.class);
                setFootView("连接成功，IP:" + ipConfig.getIp());
                ShanXunManager.sendHeart(this,ipConfig.getIp());
            }
        } else {
            setFootView("拨号失败");
        }
    }

    @Override
    public void setFootView(String s) {
        actiontarget.setText(s);
    }
}
