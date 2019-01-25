package ru.job4j.seachfiles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static org.junit.Assert.*;

public class SeachTest {

    private final File testFolder = new File(System.getProperty("java.io.tmpdir") + "test");

    @Before
    public void setUp() {
        if (!testFolder.exists()) {
            testFolder.mkdir();
        } else {
            deleteDir(testFolder);
            testFolder.mkdir();
        }
        File folder1 = new File(testFolder + File.separator + "folder1");
        folder1.mkdir();
        File folder2 = new File(testFolder + File.separator + "folder2");
        folder2.mkdir();
        List<File> files = new ArrayList<>();
        files.add(new File(testFolder + File.separator + "file1.txt"));
        files.add(new File(testFolder + File.separator + "file2.txt"));
        files.add(new File(folder1 + File.separator + "file3.txt"));
        files.add(new File(folder2 + File.separator + "file4.txt"));
        files.add(new File(testFolder + File.separator + "file1.pdf"));
        files.add(new File(folder2 + File.separator + "file2.pdf"));
        files.add(new File(folder1 + File.separator + "file1.java"));

        for (File file : files) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @After
    public void tearDown() {
        deleteDir(testFolder);
    }

    @Test
    public void whenRequiredTxtFilesShouldReturnAllTxtFiles() {
        List<File> list = new Seach().files(testFolder.toString(), Arrays.asList("txt"));
        assertEquals(4, list.size());
        assertTrue(list.stream().anyMatch(file -> file.toString().contains("file1.txt")));
        assertTrue(list.stream().anyMatch(file -> file.toString().contains("file2.txt")));
        assertTrue(list.stream().anyMatch(file -> file.toString().contains("file3.txt")));
        assertTrue(list.stream().anyMatch(file -> file.toString().contains("file4.txt")));
    }

    @Test
    public void whenRequiredPdfFilesShouldReturnAllPdfFiles() {
        List<File> list = new Seach().files(testFolder.toString(), Arrays.asList("pdf"));
        assertEquals(2, list.size());
        assertTrue(list.stream().anyMatch(file -> file.toString().contains("file1.pdf")));
        assertTrue(list.stream().anyMatch(file -> file.toString().contains("file2.pdf")));
    }

    @Test
    public void whenRequiredPdfAndJavaFilesShouldReturnAllPdfAndJavaFiles() {
        List<File> list = new Seach().files(testFolder.toString(), Arrays.asList("pdf", "java"));
        assertEquals(3, list.size());
        assertTrue(list.stream().anyMatch(file -> file.toString().contains("file1.pdf")));
        assertTrue(list.stream().anyMatch(file -> file.toString().contains("file2.pdf")));
        assertTrue(list.stream().anyMatch(file -> file.toString().contains("file1.java")));
    }

    @Test
    public void whenRequiredDocFilesShouldReturnEmptyList() {
        List<File> list = new Seach().files(testFolder.toString(), Arrays.asList("doc"));
        assertTrue(list.isEmpty());
    }

    @Test
    public void whenExtensionsIsNullShouldReturnAllFiles() {
        List<File> list = new Seach().files(testFolder.toString(), null);
        assertEquals(7, list.size());
    }

    private void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (!Files.isSymbolicLink(f.toPath())) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
    }
}