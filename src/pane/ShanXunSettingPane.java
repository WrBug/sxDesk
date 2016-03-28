package pane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Action;
import model.Event;
import model.bean.Config;
import utils.FileUtil;
import utils.TextUtil;

/**
 * Created by wangtao on 2016-03-28.
 */
public class ShanXunSettingPane extends GridPane implements EventHandler<ActionEvent> {
    private static ShanXunSettingPane instance;
    TextField userTextField;
    TextField pwBox;
    Config config;
    CheckBox checkBox;
    private Event event;

    private ShanXunSettingPane(Event event) {
        this.event = event;
        config = FileUtil.readConfig();
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 0, 25));

        Label userName = new Label("闪讯账号:");
        add(userName, 0, 1);
        userTextField = new TextField();
        userTextField.setText(config.getSxAcount());
        add(userTextField, 1, 1);

        Label pw = new Label("闪讯密码:");
        add(pw, 0, 2);
        pwBox = new TextField();
        pwBox.setText(config.getSxPassword());
        pwBox.setCache(true);
        add(pwBox, 1, 2);

        checkBox = new CheckBox("发送心跳");
        checkBox.setSelected(config.getSendHeartBoolean());
        add(checkBox, 0, 3);

        Button btn = new Button("拨号");
        btn.setOnAction(this);
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        add(hbBtn, 1, 3);

    }

    public static ShanXunSettingPane instance(Event event) {
        if (instance == null) {
            instance = new ShanXunSettingPane(event);
        }
        return instance;
    }

    @Override
    public void handle(ActionEvent event) {
        String acount = userTextField.getCharacters().toString();
        String pswd = pwBox.getCharacters().toString();
        if (TextUtil.isEmpty(acount, pswd)) {
            this.event.setFootView("输入有误，请检查");
            return;
        }
        Config c = new Config();
        c.setSxAcount(acount);
        c.setSxPassword(pswd);
        c.setSendHeart(checkBox.isSelected());
        FileUtil.writeConfig(c);
        config = FileUtil.readConfig();
        if (TextUtil.isEmpty(config.getRouterPassword(), config.getRouterIpAddress(), config.getRouterUsername())) {
            this.event.action(Action.CHECKROUTER);
            return;
        }
        this.event.action(Action.DIAL);
    }
}
