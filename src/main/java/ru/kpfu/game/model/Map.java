package ru.kpfu.game.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Map {
    private int[][] map;
    private static Map myMap;
    private static int countUser;
    private static Player players[];
    private static User myPlayer;

    public static Player[] getPlayers() {
        return players;
    }

    public static void setCountUser(int countUser) {
        myMap.countUser = countUser;
    }

    public static int getCountUser() {
        return countUser;
    }

    public static Map getMyMap() {
        return myMap;
    }

    public static User createMyPlayer(int id) {
        return myPlayer = new User(new SimpleIntegerProperty(players[id].getX()), new SimpleIntegerProperty(players[id].getY()), id);
    }

    public static User getMyPlayer() {
        return myPlayer;
    }

    public void create(int i) {
        myMap = this;
        try {
            Scanner sc = new Scanner(new FileReader("/Users/catch_you/IdeaProjects/ITIS/3semestr/Semestrovka2Client/src/main/java/resourse/map" + i + ".txt"));
            int n = sc.nextInt();
            int m = sc.nextInt();
            map = new int[n][m];
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < m; k++) {
                    map[j][k] = sc.nextInt();
                }
            }
            int playersCount = sc.nextInt();
            players = new Player[playersCount];
            for (int j = 0; j < playersCount; j++) {
                players[j] = new Player(sc.nextInt(), sc.nextInt(), j);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int[][] getMap() {
        return map;
    }

}
