package app.pane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import app.model.Action;
import app.model.Event;
import app.model.bean.Config;
import app.utils.FileUtil;
import app.utils.TextUtil;

/**
 * Created by wangtao on 2016-03-28.
 */
public class RouterPane extends GridPane implements EventHandler<ActionEvent> {
    private static RouterPane instance;
    private Event event;
    private Config config;
    TextField routerIpField;
    TextField routerUserField;
    TextField routerPassField;

    public RouterPane(Event event) {
        this.event = event;
        config = FileUtil.readConfig();
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        Label label = new Label("路由器地址：");
        add(label, 0, 1);
        routerIpField = new TextField();
        routerIpField.setText(config.getRouterIpAddress());
        add(routerIpField, 1, 1);

        label = new Label("用   户   名：");
        add(label, 0, 2);
        routerUserField = new TextField();
        routerUserField.setText(config.getRouterUsername());
        add(routerUserField, 1, 2);

        label = new Label("密        码：");
        add(label, 0, 3);
        routerPassField = new TextField();
        routerPassField.setText(config.getRouterPassword());
        add(routerPassField, 1, 3);

        Button btn = new Button("保存");
        btn.setOnAction(this);
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        add(hbBtn, 1, 4);
    }

    public static RouterPane instance(Event event) {
        if (instance == null) {
            instance = new RouterPane(event);
        }
        return instance;
    }

    @Override
    public void handle(ActionEvent event) {
        String ip = routerIpField.getCharacters().toString();
        String user = routerUserField.getCharacters().toString();
        String pass = routerPassField.getCharacters().toString();
        if (TextUtil.isEmpty(ip, user, pass)) {
            this.event.setFootView("输入有误，请检查");
            return;
        }
        Config c = new Config();
        c.setRouterUsername(user);
        c.setRouterIpAddress(ip);
        c.setRouterPassword(pass);
        FileUtil.writeConfig(c);
        this.event.action(Action.SAVEROUTER);
    }
}
