package ru.job4j.filemanager.server;

import java.io.*;

public class FileManagerImpl implements FileManager {

    private final File rootFolder;
    private File curFolder;

    public FileManagerImpl(File rootFolder) {
        this.rootFolder = rootFolder;
        this.curFolder = rootFolder;
    }

    @Override
    public File[] getRootList() {
        return this.rootFolder.listFiles();
    }

    @Override
    public File[] toSubFolder(String folderName) {
        File file = new File(curFolder + File.separator + folderName);
        if (file.isDirectory()) {
            curFolder = file;
            return file.listFiles();
        } else {
            return null;
        }
    }

    @Override
    public File[] toRootFolder() {
        curFolder = rootFolder;
        return this.getRootList();
    }

    @Override
    public File sendFile(String fileName) {
        File file =  new File(curFolder + File.separator + fileName);
        if (file.exists() && !file.isDirectory()) {
            return file;
        } else {
            return null;
        }
    }

    @Override
    public File getFile() {
        return curFolder;
    }
}
