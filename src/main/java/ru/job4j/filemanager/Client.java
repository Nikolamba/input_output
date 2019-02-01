package ru.job4j.filemanager;

import ru.job4j.filemanager.client.ClientActionImpl;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class Client {

    private final Socket socket;
    private ClientActionImpl clientAction;

    public Client(Socket socket, File root) {
        this.socket = socket;
        this.clientAction = new ClientActionImpl(socket, root);
    }

    public void start() {
        try (OutputStream out = socket.getOutputStream();
             Scanner console = new Scanner(System.in)) {
            String consoleStr;
            this.clientAction.showMenu();
            do {
                consoleStr = console.nextLine();
                out.write((consoleStr + System.lineSeparator()).getBytes());
                out.flush();
                this.clientAction.processServerResponce();
            } while (!consoleStr.equals("exit"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Properties prop = Server.getProperties();
        int port = Integer.valueOf(prop.getProperty("port"));
        String dir = prop.getProperty("client_direct");
        try (Socket socket = new Socket(InetAddress.getByName("localhost"), port)) {
            new Client(socket, new File(dir)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
