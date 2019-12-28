package ru.kpfu.game.Help;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class SocketParmeters {
    private BufferedReader in;
    private PrintWriter out;
    private static SocketParmeters socketParmeters;

    public static void create(BufferedReader in, PrintWriter out) {
        socketParmeters = new SocketParmeters(in, out);
    }

    public static SocketParmeters getSocketParmeters() {
        return socketParmeters;
    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    private SocketParmeters(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }
}
