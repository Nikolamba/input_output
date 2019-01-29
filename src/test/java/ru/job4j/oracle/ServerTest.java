package ru.job4j.oracle;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Nikolay Meleshkin (sol.of.f@mail.ru)
 * @version 0.1
 */
public class ServerTest {

    private final static String LS = System.lineSeparator();

    @Test
    public void whenGiveExitShouldEmptyString() throws IOException {
        testServer(
                String.format("exit%s", LS),
                String.format("good luck%s%s", LS, LS)
        );
    }

    @Test
    public void whenGiveHelloOracleShouldReturnGreeting() throws IOException {
        testServer(
                String.format("Hello oracle%sexit%s", LS, LS),
                String.format("Hello, dear friend, I'm a oracle.%s%sgood luck%s%s", LS, LS, LS, LS)
        );
    }

    @Test
    public void whenGiveUnknownComandShouldReturnNotUnderstand() throws IOException {
        testServer(
                String.format("Unknown comand%sexit%s", LS, LS),
                String.format("I don't understand your question%s%sgood luck%s%s", LS, LS, LS, LS)
        );
    }

    private void testServer(String input, String expect) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);

        new Server(socket).start();

        assertThat(out.toString(), is(expect));
    }
}