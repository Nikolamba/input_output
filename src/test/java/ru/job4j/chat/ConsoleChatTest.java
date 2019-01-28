package ru.job4j.chat;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/**
 * @author Nikolay Meleshkin (sol.of.f@mail.ru)
 * @version 0.1
 */
public class ConsoleChatTest {

    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testingChat1() {
        System.setIn(new ByteArrayInputStream(
                ("test" + System.lineSeparator() + "finish" + System.lineSeparator()).getBytes()));
        new ConsoleChat().beginChat();
        System.out.println(new String(out.toByteArray()));
        assertTrue(new String(out.toByteArray()).contains("answer"));
        assertTrue(new String(out.toByteArray()).contains("End of program"));
    }

    @Test
    public void testingChat2() {
        System.setIn(new ByteArrayInputStream(("stop" + System.lineSeparator()
                + "test" + System.lineSeparator()
                +  "finish" + System.lineSeparator())
                .getBytes()));
        new ConsoleChat().beginChat();
        assertFalse(new String(out.toByteArray()).contains("answer"));
    }

    @Test
    public void testinChat3() {
        System.setIn(new ByteArrayInputStream(("stop" + System.lineSeparator()
                + "test" + System.lineSeparator()
                + "continue" + System.lineSeparator()
                + "finish" + System.lineSeparator())
                .getBytes()));
        new ConsoleChat().beginChat();
        assertEquals(1, StringUtils.countMatches(new String(out.toByteArray()), "answer"));
    }
}