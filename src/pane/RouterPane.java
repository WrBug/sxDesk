package pane;

import javafx.scene.layout.GridPane;

/**
 * Created by wangtao on 2016-03-28.
 */
public class RouterPane extends GridPane {
    private static RouterPane instance;

    public static RouterPane instance() {
        if (instance == null) {
            instance = new RouterPane();
        }
        return instance;
    }
}
