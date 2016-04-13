package app.pane;

import app.model.Action;
import app.model.Event;
import app.model.bean.Config;
import app.utils.CMD;
import app.utils.FileUtil;
import app.utils.SystemUtil;
import app.utils.TextUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;

/**
 * Created by wangtao on 2016-03-28.
 */
public class WifiPane extends GridPane implements EventHandler<ActionEvent> {
    private static WifiPane instance;
    TextField wifiNameText;
    TextField wifiPswdText;
    Config config;
    private Event event;
    Button btn;
    Hyperlink link;
    private WifiPane(Event event) {
        this.event = event;
        config = FileUtil.readConfig();
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(0, 25, 0, 25));
        Label userName = new Label("WIFI  名:");
        add(userName, 0, 1);
        wifiNameText = new TextField();
        wifiNameText.setText(TextUtil.isEmpty(config.getWifiName()) ? "shanxunWifi" : config.getWifiName());
        add(wifiNameText, 1, 1, 2, 1);
        Label pw = new Label("WIFI密码:");
        add(pw, 0, 2);
        wifiPswdText = new TextField();
        wifiPswdText.setText(config.getWifiPswd());
        wifiPswdText.setCache(true);
        add(wifiPswdText, 1, 2, 2, 1);
        link=new Hyperlink("使用教程>>");
        link.setOnAction(event1 -> {
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://wifi.mandroid.cn");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        add(link,0,3,2,1);
        btn = new Button("开启");
        btn.setOnAction(this);
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        add(hbBtn, 2, 3);
    }

    public static WifiPane instance(Event event) {
        if (instance == null) {
            instance = new WifiPane(event);
        }
        return instance;
    }

    @Override
    public void handle(ActionEvent event) {
        String acount = wifiNameText.getCharacters().toString();
        String pswd = wifiPswdText.getCharacters().toString();
        if (TextUtil.isEmpty(acount, pswd)) {
            this.event.setFootView("输入有误，请检查");
            return;
        }
        if (pswd.length() < 8) {
            this.event.setFootView("密码最少8位");
            return;
        }
        Config c = new Config();
        c.setWifiName(acount);
        c.setWifiPswd(pswd);
        FileUtil.writeConfig(c);
        config = FileUtil.readConfig();
        String name = ((Button) event.getSource()).getText();
        if ("开启".equals(name)) {
            openWifi();
        }else if("关闭".equals(name)){
            stopWifi();
        }
    }

    private void stopWifi() {
        try {
            String info=CMD.execute("netsh wlan stop hostednetwork");
            System.out.println(info);
            if(info.contains("已停止")){
                event.setFootView(info.substring(0,info.lastIndexOf("。")+1));
                btn.setText("开启");
            }
        } catch (Exception e) {
            event.setFootView("关闭失败");
        }
    }

    private void openWifi() {
        try {
            String info = CMD.execute("netsh wlan set hostednetwork mode=allow ssid=" + config.getWifiName() + " key=" + config.getWifiPswd());
            System.out.println(info);
            if (!info.contains("已成功")) {
                event.setFootView("开启失败，请检查无线模块");
                return;
            }
            info = CMD.execute("netsh wlan start hostednetwork");
            System.out.println(info);
            if (!info.contains("已启动")) {
                event.setFootView("开启失败，请检查无线模块");
                return;
            }
            event.setFootView(info.substring(0,info.lastIndexOf("。")+1));
            btn.setText("关闭");
        } catch (Exception e) {
            event.setFootView("开启失败，请检查无线模块");
        }
    }
}
