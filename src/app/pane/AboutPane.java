package app.pane;

import app.utils.Constant;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
        text = new Label("本软件完全免费，支持大多老版的TP-LINK、水星等路由器，项目已开源，请勿倒卖，软件更新请关注下面的网址:");
        text.setWrapText(true);
        add(text, 0, 1);
        Hyperlink link = new Hyperlink();
        link.setText("http://www.mandroid.cn");
        link.setOnAction(event1 -> {
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://www.mandroid.cn");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        add(link, 0, 2);
    }

    public static AboutPane instance(Event event) {
        if (instance == null) {
            instance = new AboutPane(event);
        }
        return instance;
    }
}
