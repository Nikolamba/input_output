package ru.job4j.chat;

import java.io.*;
import java.net.URISyntaxException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikolay Meleshkin (sol.of.f@mail.ru)
 * @version 0.1
 */
public class ConsoleChat {

    private final File logFile = new File("log.txt");
    private String answers = "answers.txt";
    private Map<Integer, Integer> fileSize;

    public ConsoleChat() {
        try {
            if (!logFile.exists()) {
                System.out.println("Creating log.txt file: " + logFile.createNewFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileSize = getFileSize();
    }

    public void beginChat() {
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
                    System.out.println(saveToLog(getNextRandomLine()));
                }
                userLine = saveToLog(userReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(saveToLog("End of program " + new GregorianCalendar().getTime()));
    }

    private String getNextRandomLine() {
        String result = null;
        try (RandomAccessFile raf = new RandomAccessFile(new File(getClass().getResource(answers).toURI()), "r")) {
            long randLine = (long) (Math.random() * (fileSize.size()));
            raf.seek(skip(randLine));
            result = raf.readLine();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    private long skip(long numLine) {
        long result = 0;
        for (int i = 0; i < numLine; i++) {
            result += fileSize.get(i);
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

    private Map<Integer, Integer> getFileSize() {
        Map<Integer, Integer> result = new HashMap<>();
        int size = 0;
        try (InputStream is = getClass().getResourceAsStream(answers);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.put(size, line.length() + 2);
                size++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        new ConsoleChat().beginChat();
    }
}
