package ru.itis.sockets.helper;

import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(names = {"--port"}, description = "port client")
    private int port;


    @Parameter(names = {"--server-ip"}, description = "server-ip server")
    private String serverIp;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
