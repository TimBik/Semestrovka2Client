package ru.kpfu.game.Help;

import javafx.scene.Node;
import ru.kpfu.game.room.GameStart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Removing {
    private Collection<NodeAndCoordinates> nodes;
    static private Removing me;

    private Removing() {
        this.nodes = new CopyOnWriteArrayList<>();
    }

    public synchronized static Removing getRemovin() {
        if (me == null) me = new Removing();
        return me;
    }

    public synchronized void add(Node nodeFromGridPane, int x, int y) {
        nodes.add(new NodeAndCoordinates(nodeFromGridPane, x, y));
    }

    public synchronized Collection<NodeAndCoordinates> getList() {
        return nodes;
    }
}
