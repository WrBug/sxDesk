package app.pane;

import javafx.scene.layout.GridPane;
import app.model.Event;

/**
 * Created by wangtao on 2016-03-29.
 */
public class AboutPane extends GridPane {
    private static AboutPane instance;
    private Event event;

    public AboutPane(Event event) {
        this.event = event;
    }

    public static AboutPane instance(Event event) {
        if (instance == null) {
            instance = new AboutPane(event);
        }
        return instance;
    }
}
