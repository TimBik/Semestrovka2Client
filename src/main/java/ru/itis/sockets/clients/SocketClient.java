package ru.itis.sockets.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;


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
            startConnection(InetAddress.getByName(ip),port);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("адрес по " + ip + " не доступен");
        }
    }

    public void startConnection(InetAddress inetAddress,int port){
        try {
            // создаем подключение
            clientSocket = new Socket(inetAddress, port);
            // получили выходной поток
            out = new PrintWriter(clientSocket.getOutputStream());
            // входной поток
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // запустили слушателя сообщений
            new Thread(receiverMessagesTask).start();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void sendMessage(String message) {
        out.println(message);
        out.flush();
    }

    private Runnable receiverMessagesTask = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    String response = in.readLine();
                    if (response != null) {
                        if(response.equals("bye")){
                            System.out.println("Сервер прощается с вами");
                            Thread.currentThread().interrupt();
                            stopConnection();
                            break;
                        }else {
                            System.out.println(response);
                        }
                    }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
            System.out.println("Я больше ничего не отправлю");
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
