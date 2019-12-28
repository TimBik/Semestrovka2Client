package ru.kpfu.game.Help;

import javafx.util.Pair;

public class Trio {
    int x;
    int y;
    int num;
    String path;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Trio(int x, int y, int num, String path) {
        this.x = x;
        this.y = y;
        this.num = num;
        this.path = path;
    }
}
