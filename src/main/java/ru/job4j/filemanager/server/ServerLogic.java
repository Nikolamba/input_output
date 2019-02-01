package ru.job4j.filemanager.server;

import ru.job4j.filemanager.IOData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ServerLogic {

    private final static String LS = System.lineSeparator();

    private final FileManager fileManager;
    private List<UserAction> actions;
    private IOData ioData;

    public ServerLogic(IOData ioData, File rootFolder) {
        fileManager = new FileManagerImpl(rootFolder);
        this.ioData = ioData;
        this.actions = new ArrayList<>();

        this.actions.add(new GetRootList(0, "Get list root folder"));
        this.actions.add(new ToSubFolder(1, "Go to subfolder"));
        this.actions.add(new ToRootFolder(2, "Go to root folder"));
        this.actions.add(new SendFile(3, "Send file to client"));
        this.actions.add(new GetFile(4, "Send file to server"));
        this.actions.add(new ShowMenu(5, "Show menu"));
    }

    public void select(int action) {
        this.actions.get(action).execute();
    }

    public int actionSize() {
        return this.actions.size();
    }

    private class GetRootList implements UserAction {

        private int key;
        private String info;

        GetRootList(int key, String info) {
            this.key = key;
            this.info = info;
        }

        @Override
        public int getKey() {
            return key;
        }

        @Override
        public void execute() {
            File[] files = fileManager.getRootList();
            ioData.sendObject(files);
        }

        @Override
        public String info() {
            return info;
        }
    }

    private class  ToSubFolder implements UserAction {

        private int key;
        private String info;

        ToSubFolder(int key, String info) {
            this.key = key;
            this.info = info;
        }

        @Override
        public int getKey() {
            return key;
        }

        @Override
        public void execute() {
            ioData.sendString("Enter folder name");
            String folderName = ioData.getString();
            File[] files = fileManager.toSubFolder(folderName);
            if (files != null) {
                ioData.sendObject(files);
            } else {
                ioData.sendString("No such folder");
            }
        }

        @Override
        public String info() {
            return info;
        }
    }

    private class ToRootFolder implements UserAction {

        private int key;
        private String info;

        ToRootFolder(int key, String info) {
            this.key = key;
            this.info = info;
        }

        @Override
        public int getKey() {
            return key;
        }

        @Override
        public void execute() {
            File[] files = fileManager.toRootFolder();
            ioData.sendObject(files);
        }

        @Override
        public String info() {
            return info;
        }
    }

    private class SendFile implements UserAction {

        private int key;
        private String info;

        SendFile(int key, String info) {
            this.key = key;
            this.info = info;
        }

        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public void execute() {
            ioData.sendString("Enter file name which you want to get");
            String fileName = ioData.getString();
            File file = fileManager.sendFile(fileName);
            if (file != null) {
                ioData.sendFile(file, fileName);
            } else {
                ioData.sendString("No such file");
            }
        }

        @Override
        public String info() {
            return this.info;
        }
    }

    private class GetFile implements UserAction {

        private int key;
        private String info;

        GetFile(int key, String info) {
            this.key = key;
            this.info = info;
        }

        @Override
        public int getKey() {
            return key;
        }

        @Override
        public void execute() {
            ioData.sendString("Enter file name to send");
            String fileName = ioData.getString();
            ioData.sendRequest(fileName);
            File curDir = fileManager.getFile();
            ioData.getString();
            ioData.getFile(curDir, fileName);
        }

        @Override
        public String info() {
            return info;
        }
    }

    private class ShowMenu implements UserAction {

        private int key;
        private String info;

        ShowMenu(int key, String info) {
            this.key = key;
            this.info = info;
        }

        @Override
        public int getKey() {
            return key;
        }

        @Override
        public void execute() {
            StringBuilder sb = new StringBuilder();
            sb.append("-----------------------------");
            sb.append(LS);
            for (UserAction action : actions) {
                sb.append(action.getKey());
                sb.append(". ");
                sb.append(action.info());
                sb.append(LS);
            }
            sb.append("-----------------------------");
            sb.append(LS);
            sb.append(LS);
            ioData.sendMenu(sb.toString());
        }

        @Override
        public String info() {
            return info;
        }
    }
}
