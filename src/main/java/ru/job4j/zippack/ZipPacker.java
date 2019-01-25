package ru.job4j.zippack;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Nikolay Meleshkin (sol.of.f@mail.ru)
 * @version 0.1
 */
public class ZipPacker {

    private static final int BUFFER_SIZE = 4096;

    public void compressFiles(File source, String[] exts, String destZipFile) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destZipFile))) {
                if (source.isDirectory()) {
                    addFolderToZip(source, source.getName(), zos, exts);
                } else {
                    throw new IllegalArgumentException("the specified resource is not a folder");
                }
            zos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addFolderToZip(File folder, String parentFolder, ZipOutputStream zos, String[] exts) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                addFolderToZip(file, parentFolder + "/" + file.getName(), zos, exts);
                continue;
            }
            String fileExt = getExtFile(file);
            if (Arrays.stream(exts).anyMatch(fileExt::equals)) {
                addFileToZip(file, parentFolder, zos);
            }
        }
    }

    private void addFileToZip(File file, String parentFolder, ZipOutputStream zos) throws IOException {
        zos.putNextEntry(new ZipEntry(parentFolder + "/" + file.getName()));

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = bis.read(bytesIn)) != -1) {
            zos.write(bytesIn, 0, read);
        }
        zos.closeEntry();
    }

    private String getExtFile(File file) {
        String[] parts = file.getName().split("\\.");
        return parts[parts.length - 1];
    }

    private static Map<String, String> loadArgs(String[] args) {
        Map<String, String> argsMap = new HashMap<>();
        String str = null;
        for (String arg : args) {
            if (str != null) {
                argsMap.put(str, arg);
                str = null;
            }
            if (arg.equals("-d")) {
                str = "source";
            } else if (arg.equals("-e")) {
                str = "extensions";
            } else if (arg.equals("-o")) {
                str = "filename";
            }
        }
        return argsMap;
    }

    public static void main(String[] args) {
        Map<String, String> argsMap = loadArgs(args);
        if (argsMap.get("source") == null || argsMap.get("extensions") == null || argsMap.get("filename") == null) {
            throw new IllegalArgumentException("invalid arguments");
        }
        new ZipPacker().compressFiles(new File(argsMap.get("source")),
                argsMap.get("extensions").split(","),
                argsMap.get("filename"));
    }
}
