package ru.job4j.dropabuses;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Nikolay Meleshkin (sol.of.f@mail.ru)
 * @version 0.1
 */
public class DropAduses {
    public void dropAbuses(InputStream in, OutputStream out, String[] abuse) {
        try (Scanner lineScanner = new Scanner(in)) {
            while (lineScanner.hasNextLine()) {
                String line = lineScanner.nextLine();
                try (Scanner wordScanner = new Scanner(line)) {
                    while (wordScanner.hasNext()) {
                        String word = wordScanner.next();
                        String trimWord = word.replaceAll("^\\W|\\W$", "");
                        boolean result = (abuse != null && abuse.length > 0)
                                && Arrays.stream(abuse).anyMatch(trimWord::equalsIgnoreCase);
                        if (!result) {
                            out.write((word).getBytes());
                        }
                        if (!result && wordScanner.hasNext()) {
                            out.write(" ".getBytes());
                        }
                    }
                }
                out.write(System.lineSeparator().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
