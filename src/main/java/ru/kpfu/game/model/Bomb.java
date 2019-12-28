package ru.kpfu.game.model;

import ru.kpfu.game.Help.SocketParmeters;

import java.io.PrintWriter;

public class Bomb {
    int x;
    int y;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void outBomb() {
        PrintWriter out = SocketParmeters.getSocketParmeters().getOut();
        out.println("bomb " + x + " " + y);
    }

}
