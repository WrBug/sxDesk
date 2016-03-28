package app;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pane.RouterPane;
import pane.ShanXunSettingPane;

public class Main extends Application {
    Tab dialTab;
    Tab routerTab;
    BorderPane borderPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initView(primaryStage);
    }

    private void initView(Stage primaryStage) {
        primaryStage.setTitle("闪讯wifi助手");
        borderPane = new BorderPane();
        borderPane.setTop(getTab());
//        borderPane.setCenter(s);
        Scene scene = new Scene(borderPane, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TabPane getTab() {
        TabPane tabPane = new TabPane();
        dialTab = new Tab("拨号");
        dialTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue != newValue && newValue == true) {
                    borderPane.setCenter(ShanXunSettingPane.instance());
                } else {
                    borderPane.setCenter(RouterPane.instance());

                }
            }
        });
        dialTab.setClosable(false);
        tabPane.getTabs().add(dialTab);
        routerTab = new Tab("路由器配置");
        routerTab.setClosable(false);
        tabPane.getTabs().add(routerTab);
        return tabPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
