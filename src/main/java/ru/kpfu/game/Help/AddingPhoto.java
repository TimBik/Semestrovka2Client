package ru.kpfu.game.Help;

import javafx.scene.Node;
import ru.kpfu.game.room.GameStart;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AddingPhoto {
    private List<Trio> nodes;
    static private AddingPhoto me;

    private AddingPhoto() {
        this.nodes = new CopyOnWriteArrayList<>();
    }

    public synchronized static AddingPhoto getAdding() {
        if (me == null) me = new AddingPhoto();
        return me;
    }

    public synchronized void add(Trio trio) {
        nodes.add(trio);
    }

    public synchronized List<Trio> getList() {
        return nodes;
    }
}
