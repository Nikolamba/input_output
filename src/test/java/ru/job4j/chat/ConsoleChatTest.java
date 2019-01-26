package ru.job4j.chat;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.CharBuffer;

import static org.junit.Assert.*;

public class ConsoleChatTest {

    private final File answers = new File(System.getProperty("java.io.tmpdir") + File.separator + "answers.txt");
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        try {
            if (!answers.exists()) {
                System.out.println("Creating testing file answers.txt: " + answers.createNewFile());
            } else {
                System.out.println("File answers.txt is alredy exists");
                System.out.println("Deleting answers.txt: " + answers.delete());
                System.out.println("Creating testing file answers.txt: " + answers.createNewFile());
            }

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(answers));
            fileWriter.write("first answer" + System.lineSeparator());
            fileWriter.write("second answer" + System.lineSeparator());
            fileWriter.write("third answer" + System.lineSeparator());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testingChat1() {
        System.setIn(new ByteArrayInputStream(
                ("test" + System.lineSeparator() + "finish" + System.lineSeparator()).getBytes()));
        new ConsoleChat().beginChat(answers);
        assertTrue(new String(out.toByteArray()).contains("answer"));
        assertTrue(new String(out.toByteArray()).contains("End of program"));
    }

    @Test
    public void testingChat2() {
        System.setIn(new ByteArrayInputStream(("stop" + System.lineSeparator()
                + "test" + System.lineSeparator()
                +  "finish" + System.lineSeparator())
                .getBytes()));
        new ConsoleChat().beginChat(answers);
        assertFalse(new String(out.toByteArray()).contains("answer"));
    }

    @Test
    public void testinChat3() {
        System.setIn(new ByteArrayInputStream(("stop" + System.lineSeparator()
                + "test" + System.lineSeparator()
                + "continue" + System.lineSeparator()
                + "finish" + System.lineSeparator())
                .getBytes()));
        new ConsoleChat().beginChat(answers);
        assertEquals(1, StringUtils.countMatches(new String(out.toByteArray()), "answer"));
    }
}