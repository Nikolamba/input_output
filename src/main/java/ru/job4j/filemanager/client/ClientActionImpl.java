package ru.job4j.filemanager.client;

import ru.job4j.filemanager.Client;
import ru.job4j.filemanager.IOData;

import java.io.*;
import java.net.Socket;

public class ClientActionImpl implements ClientAction {

    private IOData ioData;
    private final File curFolder;

    public ClientActionImpl(Socket socket, File root) {
        try {
            this.ioData = new IOData(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.curFolder = root;
    }

    public void processServerResponce() {
        String[] answer = ioData.getString().split("---/");
        switch (answer[0]) {
            case "STRING": showString(); break;
            case "OBJECT": showFiles(); break;
            case "FILE": getFile(answer[1]); break;
            case "MENU": showMenu(); break;
            case "REQ_FILE": sendFile(answer[1]); break;
            default:
                System.out.println("Unknown data type");
        }
    }

    @Override
    public void showMenu() {
        System.out.println(ioData.getMenu());
        System.out.println("Select action...");
    }

    @Override
    public void getFile(String fileName) {
        ioData.getFile(curFolder, fileName);
        System.out.println("File downloaded. Select action...");
    }

    @Override
    public void sendFile(String fileName) {
        File file = new File(curFolder + File.separator + fileName);
        ioData.sendFile(file, fileName);
        System.out.println("File sent. Select action...");
    }

    @Override
    public void showString() {
        System.out.println(ioData.getString());
    }

    @Override
    public void showFiles() {
        File[] files = ioData.getObject();
        for (File file : files) {
            System.out.println(file.toString());
        }
        System.out.println("Select action...");
    }
}
