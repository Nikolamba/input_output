package ru.job4j.filemanager.server;

public interface UserAction {
    int getKey();
    void execute();
    String info();
}
