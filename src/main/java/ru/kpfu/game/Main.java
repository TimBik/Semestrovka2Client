package ru.kpfu.game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.kpfu.game.Help.SocketParmeters;
import ru.kpfu.game.clients.SocketClient;
import ru.kpfu.game.menu.Menu;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("menu/menu.fxml"));
        Menu menu = new Menu();
        menu.start(primaryStage);
    }


    public static void main(String[] args) {
        new SocketClient().startConnection("localhost", 9753);
        launch(args);
    }
}
