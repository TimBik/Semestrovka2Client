package ru.kpfu.game.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import ru.kpfu.game.Help.GridContainer;
import ru.kpfu.game.Help.Removing;
import ru.kpfu.game.Help.SocketParmeters;

import java.io.PrintWriter;

public class User {
    private SimpleIntegerProperty x;
    private SimpleIntegerProperty y;
    private int id;
    private static User myUser;


    public User(SimpleIntegerProperty x, SimpleIntegerProperty y, int id) {
        this.x = x;
        this.x.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                PrintWriter out = SocketParmeters.getSocketParmeters().getOut();
                out.println("new_coordinates " + newValue + " " + getY());
                System.out.println("new coordinate: " + newValue + " " + getY());
            }
        });
        this.y = y;
        this.y.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                PrintWriter out = SocketParmeters.getSocketParmeters().getOut();
                out.println("new_coordinates " + getX() + " " + newValue);
                System.out.println("new coordinate: " + getX() + " " + newValue);
            }
        });
        this.id = id;
    }

    public static void create(int id) {
        myUser = Map.createMyPlayer(id);
    }

    public static User getUser() {
        return myUser;
    }

    public int getX() {
        return x.get();
    }

    public SimpleIntegerProperty xProperty() {
        return x;
    }

    public void setX(int x) {
        if (chek(x, y.get())) {
            this.x.set(x);
        } else {
            //GridPane pane = GridContainer.getGridPane();
            //Removing.getRemovin().add(GridContainer.getNodeFromGridPane(pane, this.x.get(), y.get()));
            // this.x.set(this.x.getValue());
        }
    }

    public int getY() {
        return y.get();
    }

    public SimpleIntegerProperty yProperty() {
        return y;
    }

    public void setY(int y) {
        if (chek(x.get(), y)) {
            this.y.set(y);
        } else {
            //GridPane pane = GridContainer.getGridPane();
            //Removing.getRemovin().add(GridContainer.getNodeFromGridPane(pane, x.get(), this.y.get()));
            //this.y.set(this.y.getValue());
        }
    }

    private boolean chek(int x, int y) {
        int[][] map = Map.getMyMap().getMap();
        return map[x][y] == 0;
    }
}
