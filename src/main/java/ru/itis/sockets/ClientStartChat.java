package ru.itis.sockets;

import com.beust.jcommander.JCommander;
import ru.itis.sockets.helper.Args;
import ru.itis.sockets.clients.SocketClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientStartChat {
    public static void main(String[] args) {
        Args args1 = new Args();
        JCommander jcomander = new JCommander(args1);
        jcomander.parse(args);
        SocketClient client = new SocketClient();
        try {
            client.startConnection(InetAddress.getByName(args1.getServerIp()), args1.getPort());
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("адрес по "+args1.getServerIp()+" не доступен");
        }
        Scanner sc = new Scanner(System.in);
        while (true) {
            String message = sc.nextLine();
            client.sendMessage(message);
            if(message.equals("."))break;
        }
        System.out.println("Я больше ничего не прочитаю");
    }
}