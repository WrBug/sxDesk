package app.pane;

import app.model.Event;
import app.model.ShanXunManager;
import app.model.bean.Notice;
import app.utils.FileUtil;
import app.utils.TextUtil;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Created by wangtao on 2016-03-29.
 */
public class NoticePane extends GridPane {
    private static NoticePane instance;
    private Event event;
    private Notice notice;
    Label contentText;
    Label titleText;
    Hyperlink link;

    public NoticePane(Event event) {
        this.event = event;
        notice = FileUtil.readNotice();
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(0, 25, 0, 25));
        contentText = new Label();
        titleText = new Label();
        link = new Hyperlink();
        add(titleText, 0, 0);
        add(contentText, 0, 1);
        add(link, 0, 2);
        getWebData();
        setData();
    }

    private void getWebData() {
        new Thread(() -> {
            String no = ShanXunManager.getNotice();
            if (!TextUtil.isEmpty(no)) {
                notice = new Gson().fromJson(no.substring(no.indexOf("{")), Notice.class);
                FileUtil.writeNotice(notice);
            } else {
                notice.setContent("暂无公告");
            }
            Platform.runLater(() -> setData());
        }).start();
    }

    private synchronized void setData() {
        if (notice == null) {
            contentText.setText("正在获取数据……");
            contentText.setWrapText(true);
            return;
        }
        titleText.setText(notice.getTitle());
        contentText.setText(notice.getContent() == null ? "正在获取数据……" : notice.getContent());
        contentText.setWrapText(true);

        if (!TextUtil.isEmpty(notice.getUrl())) {
            link.setText(TextUtil.isEmpty(notice.getLinkText()) ? notice.getUrl() : notice.getLinkText());
            link.setOnAction(event1 -> {
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + notice.getUrl());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    public static NoticePane instance(Event event) {
        if (instance == null) {
            instance = new NoticePane(event);
        } else {
            instance.getWebData();
        }
        return new NoticePane(event);
    }
}
