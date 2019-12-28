package ru.kpfu.game.clients;

import javafx.util.Pair;
import ru.kpfu.game.Help.*;
import ru.kpfu.game.model.Bomb;
import ru.kpfu.game.model.Map;
import ru.kpfu.game.model.Player;
import ru.kpfu.game.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class SocketClient {
    // поле, содержащее сокет-клиента
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private int idUser;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    // начало сессии - получаем ip-сервера и его порт
    public void startConnection(String ip, int port) {
        try {
            startConnection(InetAddress.getByName(ip), port);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("адрес по " + ip + " не доступен");
        }
    }

    public void startConnection(InetAddress inetAddress, int port) {
        try {
            // создаем подключение
            clientSocket = new Socket(inetAddress, port);
            // получили выходной поток
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            // входной поток
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // запустили слушателя сообщений
            SocketParmeters.create(in, out);
            thread = new Thread(receiverMessagesTask);
            thread.start();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void sendMessage(String message) {
        out.println(message);
        out.flush();
    }

    private Thread thread;

    public Thread getThread() {
        return thread;
    }

    private Runnable receiverMessagesTask = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    String response = in.readLine();
                    if (response != null) {
                        String command[] = response.split(" ");
                        if (command[0].equals("map")) {
                            new Map().create(Integer.parseInt(command[1]));
                        } else if (command[0].equals("you_user")) {
                            User.create(Integer.parseInt(command[1]));
                        } else if (command[0].equals("new_user")) {
                            Map.getMyMap().setCountUser(Integer.parseInt(command[1]));
                        } else if (command[0].equals("new_coordinates")) {
                            Player player = Map.getPlayers()[Integer.parseInt(command[2])];
                            int x = Integer.parseInt(command[4]);
                            int y = Integer.parseInt(command[5]);
                            AddingPhoto.getAdding().add(new Trio(x,
                                    y, 2, new FindPath().findPath(player.getX(), player.getY(), x, y)));
                            player.setX(Integer.parseInt(command[4]));
                            player.setY(Integer.parseInt(command[5]));
                        } else if (command[0].equals("bomb")) {
                            AddingPhoto.getAdding().add(new Trio(Integer.parseInt(command[1]), Integer.parseInt(command[2]), 3, Args.IMAGE_PATH_BOMB));
                        } else if (command[0].equals("bye")) {
                            System.out.println("Сервер прощается с вами");
                            Thread.currentThread().interrupt();
                            stopConnection();
                            break;
                        }
                    }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    };

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
