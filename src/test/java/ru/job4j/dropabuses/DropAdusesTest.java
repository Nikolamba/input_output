package ru.job4j.dropabuses;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class DropAdusesTest {

    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    private ByteArrayInputStream in;

    @Test
    public void whenGiveStringShouldReturnSameString() {
        String testString = "test string";
        in = new ByteArrayInputStream(testString.getBytes());
        new DropAduses().dropAbuses(in, out, null);
        assertEquals("test string" + System.lineSeparator(), out.toString());
    }

    @Test
    public void whenGiveStringShouldReturnStringDropAduse() {
        String testString = "test string, another string";
        in = new ByteArrayInputStream(testString.getBytes());
        String[] aduse = {"string"};
        new DropAduses().dropAbuses(in, out, aduse);
        assertEquals("test another " + System.lineSeparator(), out.toString());
    }

}