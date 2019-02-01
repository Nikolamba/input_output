package ru.job4j.filemanager.server;

import java.io.File;

public interface FileManager {
    File[] getRootList();
    File[] toSubFolder(String folderName);
    File[] toRootFolder();
    File sendFile(String fileName);
    File getFile();
}
