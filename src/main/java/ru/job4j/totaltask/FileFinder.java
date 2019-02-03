package ru.job4j.totaltask;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Nikolay Meleshkin (sol.of.f@mail.ru)
 * @version 0.1
 */
public class FileFinder {

    private final Map<String, String> param;

    public FileFinder(Map<String, String> param) {
        this.param = param;
    }

    public List<File> findFiles() {
        List<File> result = new ArrayList<>();
        String keyForSearch = getKeyForSearch();
        Queue<File> queueDir = new LinkedList<>();
        queueDir.offer(new File(param.get("-d")));
        while (!queueDir.isEmpty()) {
            for (File file : queueDir.poll().listFiles()) {
                if (file.isDirectory()) {
                    queueDir.offer(file);
                } else {
                    if (checkFile(file, keyForSearch)) {
                        result.add(file);
                    }
                }
            }
        }
        return result;
    }

    private boolean checkFile(File file, String keyForSearch) {
        switch (keyForSearch) {
            case "-m": return checkFileByMask(file, param.get("-n"));
            case "-f": return checkFileByFullName(file, param.get("-n"));
            case "-r": return checkFileByRegularExp(file, param.get("-n"));
            default: break;
        }
        return false;
    }

    private String getKeyForSearch() {
        for (String key : param.keySet()) {
            switch (key) {
                case "-m": return "-m";
                case "-f": return "-f";
                case "-r": return "-r";
                default: break;
            }
        }
        return null;
    }

    private boolean checkFileByFullName(File file, String find) {
        return file.getName().equalsIgnoreCase(find);
    }

    private boolean checkFileByMask(File file, String find) {
        String fileName = file.getName();
        if (!find.contains("*")) {
            return this.checkFileByFullName(file, find);
        } else {
            String[] parts = find.split("\\*");
            for (String part : parts) {
                if (part.isEmpty()) {
                    continue;
                }
                if (!fileName.contains(part)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkFileByRegularExp(File file, String find) {
        return Pattern.matches(find, file.getName());
    }
}
