package ru.kpfu.game.Help;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import javax.swing.text.html.ImageView;

public class GridContainer {
    private GridPane gridPane;
    private static GridContainer me;

    public GridContainer(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public static GridPane getGridPane() {
        return me.gridPane;
    }

    static public Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }


    public static void create(GridPane gridPane) {
        me = new GridContainer(gridPane);
    }
}
