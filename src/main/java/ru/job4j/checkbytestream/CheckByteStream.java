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
            return (scanner.hasNextInt() && scanner.nextInt() % 2 == 0);
        }
    }

    public static void main(String[] args) {
        System.out.println("Enter your number");
        boolean check = new CheckByteStream().isNumber(System.in);
        System.out.println(check);
    }

}
