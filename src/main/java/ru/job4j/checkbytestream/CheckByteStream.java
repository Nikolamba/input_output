package ru.job4j.checkbytestream;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Nikolay Meleshkin(sol.of.f@mail.ru)
 * @version 0.1
 */
public class CheckByteStream {
    public boolean isNumber(InputStream in) {
        try (Scanner scanner = new Scanner(in)) {
            if (!scanner.hasNextInt()) throw new NumberFormatException();
            return (scanner.nextInt() % 2 == 0);
        }
    }
}
