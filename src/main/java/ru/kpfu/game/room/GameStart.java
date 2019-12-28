package ru.kpfu.game.room;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import ru.kpfu.game.Help.*;
import ru.kpfu.game.model.Bomb;
import ru.kpfu.game.model.Map;
import ru.kpfu.game.model.Player;
import ru.kpfu.game.model.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

public class GameStart extends Application {
    static final int WIDTH = 700;
    static final int HEIGHT = 500;
    private User user;
    private Stage stage;
    static SimpleIntegerProperty integerProperty = new SimpleIntegerProperty(0);

    public static void addInt() {
        integerProperty.set(integerProperty.getValue() + 1);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        user = User.getUser();
        Group root = new Group();
        stage = primaryStage;

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable bomb = new Runnable() {
                    @Override
                    public void run() {
                        Collection<Bomb> bombs;
                        synchronized (bombs = Bombs.getBombs().getList()) {
                            if (bombs != null && bombs.size() > 0) {
                                for (Bomb bomb :
                                        bombs) {
                                }
                            }
                        }
                    }
                };
                Runnable updater = new Runnable() {
                    @Override
                    public synchronized void run() {
                        Collection<NodeAndCoordinates> nodes;

                        synchronized (nodes = Removing.getRemovin().getList()) {
                            if (nodes != null && nodes.size() != 0) {
                                ObservableList children = gridPane.getChildren();
                                int[][] mas = map.getMap();
                                for (NodeAndCoordinates node : nodes) {
                                    children.remove(node.getNode());
                                    nodes.remove(node);
                                    mas[node.getY()][node.getX()] = 0;

                                }
                            }
                        }
                        Collection<Trio> nodes1;
                        synchronized (nodes1 = AddingPhoto.getAdding().getList()) {
                            if (nodes1 != null && nodes1.size() != 0) {
                                int[][] mas = map.getMap();
                                for (Trio trio : nodes1) {

                                    try {
                                        ImageView imageView = new ImageView(new Image(new FileInputStream(trio.getPath()), midV, midH, false, true));
                                        gridPane.add(imageView, trio.getX(), trio.getY());
                                        nodes1.remove(trio);
                                        mas[trio.getX()][trio.getY()] = trio.getNum();
                                        if (trio.getNum() == 3) {
                                            Bombs.getBombs().add(new Bomb(trio.getX(), trio.getY()));
                                            Platform.runLater(bomb);
                                        }
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Thread.yield();
                    Platform.runLater(updater);
                }
            }

        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();


        //then you set to your node
        ImageView imageView = new ImageView(new Image(new FileInputStream(Args.IMAGE_MAP), WIDTH, HEIGHT, false, true));
        root.getChildren().

                add(imageView);
        System.out.println(user.getX() + " " + user.getY());

        createMap(map.getMap(), root);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        GridContainer.create(gridPane);
        scene.setOnKeyPressed((new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                KeyCode Key = event.getCode();
                String path = null;
                System.out.println("нажата клавиша :" + Key);
                System.out.println(user.getX() + " " + user.getY());
                if (Key == KeyCode.UP) {
                    //               gridPane.getChildren().remove(getNodeFromGridPane(gridPane, user.getX(), user.getY()));
                    user.setY(user.getY() - 1);
                    path = Args.IMAGE_PATH_PLAYER_UP;
                }
                if (Key == KeyCode.DOWN) {
//                gridPane.getChildren().remove(getNodeFromGridPane(gridPane, user.getX(), user.getY()));
                    user.setY(user.getY() + 1);
//                    path = Args.IMAGE_PATH_PLAYER_DOWN;
                }
                if (Key == KeyCode.LEFT) {
                    //               gridPane.getChildren().remove(getNodeFromGridPane(gridPane, user.getX(), user.getY()));
                    user.setX(user.getX() - 1);
//                    path = Args.IMAGE_PATH_PLAYER_LEFT;
                }
                if (Key == KeyCode.RIGHT) {
                    //             gridPane.getChildren().remove(getNodeFromGridPane(gridPane, user.getX(), user.getY()));
                    user.setX(user.getX() + 1);
//                    path = Args.IMAGE_PATH_PLAYER_RIGHT;
                }
                if (Key == KeyCode.SPACE) {
                    Bomb bomb = new Bomb(User.getUser().getX(), User.getUser().getY());
                    bomb.outBomb();
                }
                if (Key == KeyCode.ESCAPE) {
                    stage.close();
                }
                System.out.println(user.getX() + "  " + user.getY());
//                if (path != null) {
//                    try {
//                        ImageView image = new ImageView(new Image(new FileInputStream(path), midV, midH, false, true));
//                        gridPane.add(image, user.getX(), user.getY());
//
//                    } catch (FileNotFoundException e) {
//                        throw new IllegalArgumentException(e);
//                    }
//
//               }
                GameStart.this.mapSO();
            }
        }));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void mapSO() {
        int[][] map = this.map.getMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j] + "  ");
            }
            System.out.println();
        }
    }

    private GridPane gridPane;

    public static void remove(GridPane gridPane, Node node) {
        gridPane.getChildren().remove(node);
    }

    Map map = Map.getMyMap();
    int midH = (HEIGHT - (map.getMap()[0].length - 1) * 10) / map.getMap()[0].length;
    int midV = (WIDTH - (map.getMap().length - 1) * 10) / map.getMap().length;

    private void createMap(int[][] map, Group root) {

        gridPane = new GridPane();
        gridPane.setHgap(map.length);
        gridPane.setVgap(map[0].length);
        for (int i = 0; i < map[0].length; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(midH));
        }
        for (int i = 0; i < map.length; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(midV));
            Player players[] = Map.getPlayers();
            for (Player player : players) {
                map[player.getX()][player.getY()] = 2;
            }
            for (int j = 0; j < map[0].length; j++) {
                String path = null;
                switch (map[i][j]) {
                    case -1:
                        path = Args.IMAGE_PATH_INFINITY_ROCK;
                        break;
                    case 1:
                        path = Args.IMAGE_PATH_ROCK;
                        break;
                    case 2:
                        path = Args.IMAGE_PATH_PLAYER_DOWN;
                        break;
                    case 3:
                        path = Args.IMAGE_PATH_BOMB;
                }
                if (path != null) {
                    try {
                        ImageView imageView = new ImageView(new Image(new FileInputStream(path), midV, midH, false, true));
                        gridPane.add(imageView, j, i);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        root.getChildren().add(gridPane);
    }


}
