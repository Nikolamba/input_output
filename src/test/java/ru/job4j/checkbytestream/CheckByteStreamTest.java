package ru.job4j.checkbytestream;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class CheckByteStreamTest {

    @Test
    public void whenEnterEvenNumberShouldReturnTrue() {
        int evenNumber = 2;
        ByteArrayInputStream in = new ByteArrayInputStream(Integer.toString(evenNumber).getBytes());
        assertTrue(new CheckByteStream().isNumber(in));
        evenNumber = 1324;
        in = new ByteArrayInputStream(Integer.toString(evenNumber).getBytes());
        assertTrue(new CheckByteStream().isNumber(in));
    }

    @Test
    public void whenEnterOddNumberShouldReturnFalse() {
        int evenNumber = 1;
        ByteArrayInputStream in = new ByteArrayInputStream(Integer.toString(evenNumber).getBytes());
        assertFalse(new CheckByteStream().isNumber(in));
        evenNumber = 1235;
        in = new ByteArrayInputStream(Integer.toString(evenNumber).getBytes());
        assertFalse(new CheckByteStream().isNumber(in));
    }

    @Test(expected = NumberFormatException.class)
    public void whenEnterNotNumberShouldThrowException() {
        String notNumber = "1a";
        ByteArrayInputStream in = new ByteArrayInputStream(notNumber.getBytes());
        new CheckByteStream().isNumber(in);
    }
}