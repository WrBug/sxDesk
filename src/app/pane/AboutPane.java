package app.pane;

import app.model.ShanXunManager;
import app.model.bean.Version;
import app.utils.CMD;
import app.utils.Constant;
import app.utils.TextUtil;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import app.model.Event;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Created by wangtao on 2016-03-29.
 */
public class AboutPane extends GridPane {
    private static AboutPane instance;
    private Event event;

    public AboutPane(Event event) {
        this.event = event;
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(0, 25, 0, 25));
        Label text = new Label(Constant.APP_NAME + " " + Constant.APP_VERSION);
        add(text, 0, 0);
        text = new Label("本软件完全免费，支持大多老版的TP-LINK、水星等路由器，项目已开源，请勿倒卖，软件更新请关注网址:");
        text.setWrapText(true);
        add(text, 0, 1);
        Hyperlink link = new Hyperlink();
        link.setText("http://www.mandroid.cn");
        link.setOnAction(event1 -> {
            CMD.loadUrl("http://www.mandroid.cn");
        });
        add(link, 0, 2);
        Button checkUpdateBut = new Button("检查更新");
        checkUpdateBut.setOnAction(event1 -> checkVersion(event));
        add(checkUpdateBut, 0, 3);
    }

    private void checkVersion(Event event) {
        String verStr = ShanXunManager.getVersion();
        if (!TextUtil.isEmpty(verStr)) {
            Version version = new Gson().fromJson(verStr, Version.class);
            if ((Constant.APP_NAME + " " + Constant.APP_VERSION).equals(version.getVersion())) {
                event.setFootView("已是最新版本");
            } else {
                event.setFootView("软件有更新");
                CMD.loadUrl(version.getUrl());
            }
        }else {
            event.setFootView("获取失败，请重试");
        }
    }

    public static AboutPane instance(Event event) {
        if (instance == null) {
            instance = new AboutPane(event);
        }
        return instance;
    }
}
