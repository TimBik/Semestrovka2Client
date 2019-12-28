package ru.kpfu.game.menu;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.kpfu.game.Help.SocketParmeters;
import ru.kpfu.game.clients.SocketClient;
import ru.kpfu.game.model.Map;
import ru.kpfu.game.model.User;
import ru.kpfu.game.room.GameStart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Menu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        primaryStage.setTitle("Menu");
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(10));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                Button button = new Button("room: " + (i * 5 + j));
                button.setMinSize(50, 50);
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("нажата кнопка: " + button.getText());
                        String room = button.getText().split(" ")[1];
                        while (true) if (SocketParmeters.getSocketParmeters().getOut() != null) break;
                        PrintWriter out = SocketParmeters.getSocketParmeters().getOut();
                        out.println("room " + room);
                        try {
                            int last = 0;
                            while (true) {
                                if (Map.getMyMap() != null && User.getUser() != null) break;
                                Thread.yield();        //Передать управление другим потокам
                            }

                            while (true) {
                                if (last == 1) break;
                                if (last != Map.getCountUser()) {
                                    last = Map.getCountUser();
                                    System.out.println(Map.getCountUser());
                                }
                                Thread.sleep(100);
                                Thread.yield();        //Передать управление другим потокам
                            }
                            //
                            new GameStart().start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });
                gridPane.add(button, i, j);
            }
        }
        root.getChildren().add(gridPane);
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }
}
