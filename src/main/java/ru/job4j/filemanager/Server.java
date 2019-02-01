package ru.job4j.filemanager;

import ru.job4j.filemanager.server.ServerLogic;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Server {

    private ServerLogic logic;
    private IOData ioData;

    public Server(Socket socket, File root) {
        try {
            ioData = new IOData(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        logic = new ServerLogic(ioData, root);
    }

    public void start() {
        System.out.println("wait command...");
        this.logic.select(5);
        do {
            int command = this.ioData.getInt();
            if (0 <= command && command < this.logic.actionSize()) {
                logic.select(command);
            } else {
                this.ioData.sendString("Enter correct menu item...");
            }
            System.out.println("wait command ...");
        } while (true);
    }

    public static Properties getProperties() {
        String pathProp = "ru/job4j/filemanager/prop.properties";
        Properties prop = new Properties();
        InputStream inputStream = Server.class.getClassLoader().getResourceAsStream(pathProp);
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public static void main(String[] args) {
        Properties prop = Server.getProperties();
        int port = Integer.valueOf(prop.getProperty("port"));
        String dir = prop.getProperty("server_direct");
        try (Socket socket = new ServerSocket(port).accept()) {
            new Server(socket, new File(dir)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
