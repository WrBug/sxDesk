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

/**
 * Created by wangtao on 2016-03-28.
 */
public class ShanXunSettingPane extends GridPane {
    private static ShanXunSettingPane instance;
    private ShanXunSettingPane() {
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));
        Label userName = new Label("闪讯账号:");
        add(userName, 0, 1);
        TextField userTextField = new TextField();
        add(userTextField, 1, 1);

        Label pw = new Label("闪讯密码:");
        add(pw, 0, 2);
        PasswordField pwBox = new PasswordField();
        pwBox.setCache(true);
        add(pwBox, 1, 2);

        CheckBox checkBox = new CheckBox("发送心跳");
        checkBox.setSelected(true);
        add(checkBox, 0, 3);

        Button btn = new Button("拨号");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.print(userTextField.getCharacters());
            }
        });
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        add(hbBtn, 1, 3);

        final Text actiontarget = new Text();
        actiontarget.setText("testtesttesttesttesttesttes");
        actiontarget.setCache(true);
        actiontarget.setFill(Color.RED);
        add(actiontarget, 0, 5, 2, 1);
        setPadding(new Insets(0, 10, 0, 10));
    }
    public static ShanXunSettingPane instance(){
        if(instance==null){
            instance=new ShanXunSettingPane();
        }
        return instance;
    }
}
