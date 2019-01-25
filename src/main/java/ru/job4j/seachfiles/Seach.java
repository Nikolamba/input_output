package ru.job4j.seachfiles;

import java.io.File;
import java.util.*;

public class Seach {

    public List<File> files(String parent, List<String> exts) {
        boolean extsIsNull = (exts == null || exts.isEmpty());
        List<File> resultFiles = new ArrayList<>();
        File fileParent = new File(parent);
        if (!fileParent.isDirectory()) {
            throw new IllegalArgumentException("the specified resource is not a folder");
        }
        Queue<File> queueDir = new LinkedList<>();
        queueDir.offer(fileParent);
        while (!queueDir.isEmpty()) {
            for (File file : queueDir.poll().listFiles()) {
                if (file.isDirectory()) {
                    queueDir.offer(file);
                } else {
                    String fileExt = getExtFile(file);
                    if (!extsIsNull && exts.stream().anyMatch(fileExt::equals)) {
                        resultFiles.add(file);
                    }
                    if (extsIsNull) {
                        resultFiles.add(file);
                    }
                }
            }
        }
    return resultFiles;
    }

    private String getExtFile(File file) {
        String[] parts = file.getName().split("\\.");
        return parts[parts.length - 1];
    }
}
