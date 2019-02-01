package ru.job4j.filemanager.client;

public interface ClientAction {
    void showMenu();
    void getFile(String fileName);
    void sendFile(String fileName);
    void showString();
    void showFiles();
}
