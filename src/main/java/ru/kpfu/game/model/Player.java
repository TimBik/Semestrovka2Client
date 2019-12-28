package ru.kpfu.game.model;

import com.sun.org.apache.xpath.internal.Arg;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import ru.kpfu.game.Help.*;
import ru.kpfu.game.room.GameStart;

import java.awt.*;
import java.util.Collection;

public class Player {
    SimpleIntegerProperty x;
    SimpleIntegerProperty y;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x.get();
    }

    public SimpleIntegerProperty xProperty() {
        return x;
    }

    public void setX(int x) {
        if (x != this.x.get()) {
            this.x.set(x);
        }
    }

    public int getY() {
        return y.get();
    }

    public SimpleIntegerProperty yProperty() {
        return y;
    }

    public void setY(int y) {
        if (y != this.y.get()) {
            this.y.set(y);
        }
    }

    public Player(SimpleIntegerProperty x, SimpleIntegerProperty y, int id) {
        this.x = x;
        this.x.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int map[][] = Map.getMyMap().getMap();
                GridPane pane = GridContainer.getGridPane();
                Node node = GridContainer.getNodeFromGridPane(pane, (int) oldValue, y.get());
                Removing.getRemovin().add(node, y.get(), (int) oldValue);
                // AddingPhoto.getAdding().add(new Trio(x.get(), (Integer) newValue, new FindPath().findPath(x.get(), (int) oldValue, x.get(), (int) newValue)));
//                map[y.get()][(int) oldValue] = 0;
//                map[y.get()][(int) newValue] = 2;
            }
        });
        this.y = y;
        this.y.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int map[][] = Map.getMyMap().getMap();
                GridPane pane = GridContainer.getGridPane();
                Node node = GridContainer.getNodeFromGridPane(pane, x.get(), (int) oldValue);
                Removing.getRemovin().add(node, (Integer) oldValue, x.get());
                // AddingPhoto.getAdding().add(new Trio(x.get(), (Integer) newValue, new FindPath().findPath(x.get(), (int) oldValue, x.get(), (int) newValue)));
//                map[(int) oldValue][x.get()] = 0;
//                map[(int) newValue][x.get()] = 2;
            }

        });
        this.id = id;
    }


    public Player(int x, int y, int id) {
        this(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y), id);
    }
}
