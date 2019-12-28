package ru.kpfu.game.Help;

import javafx.scene.Node;

public class NodeAndCoordinates {
    Node node;
    int x;
    int y;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

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

    public NodeAndCoordinates(Node node, int x, int y) {
        this.node = node;
        this.x = x;
        this.y = y;
    }
}
