package ru.job4j.chat;

import java.io.*;
import java.util.GregorianCalendar;
import java.util.Random;

public class ConsoleChat {

    private final File logFile = new File("log.txt");

    public ConsoleChat() {
        try {
            if (!logFile.exists()) {
                System.out.println("Creating log.txt file: " + logFile.createNewFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void beginChat(File answerSource) {
        System.out.println(saveToLog("Program start: " + new GregorianCalendar().getTime()));
        try (BufferedReader userReader =
                     new BufferedReader(new InputStreamReader(new BufferedInputStream(System.in)))) {

            String userLine = saveToLog(userReader.readLine());
            boolean stop = false;
            while (!userLine.equals("finish")) {
                switch (userLine) {
                    case "stop":
                        stop = true;
                        break;
                    case "continue":
                        stop = false;
                        break;
                    default: break;
                }
                if (!stop) {
                    System.out.println(saveToLog(getNextRandomLine(answerSource)));
                }
                userLine = saveToLog(userReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(saveToLog("End of program " + new GregorianCalendar().getTime()));
    }

    private String getNextRandomLine(File answerSource) {
        String result = "Empty string";
        try (BufferedReader fileReader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(answerSource)))) {
            Random r = new Random();
            result = fileReader.lines().min((o1, o2) -> r.nextBoolean() ? 1 : -1).orElse("Empty string");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String saveToLog(String string) {
        try (BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile, true))) {
            logWriter.write(string + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    public static void main(String[] args) {
        new ConsoleChat().beginChat(new File("C://answers.txt"));
    }
}
