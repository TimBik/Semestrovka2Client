package ru.kpfu.game.Help;

import ru.kpfu.game.model.Bomb;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class Bombs {
    static Bombs me;
    Collection<Bomb> bombs;

    private Bombs() {
        bombs = new CopyOnWriteArrayList<>();
    }

    public static Bombs getBombs() {
        if (me == null) me = new Bombs();
        return me;
    }

    public void add(Bomb bomb) {
        bombs.add(bomb);
    }

    public Collection<Bomb> getList() {
        return bombs;
    }
}
