package ru.job4j.oracle;

import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Nikolay Meleshkin (sol.of.f@mail.ru)
 * @version 0.1
 */
public class ClientTest {

    private final static String LS = System.lineSeparator();

    @Test
    public void whenGiveExitFromConsoleShouldExitAndPrintMessageToConsole() throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(String.format("some string%s%s", LS, LS).getBytes());
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
        System.setIn(new BufferedInputStream(new ByteArrayInputStream(
                String.format("exit%s", LS).getBytes()
        )));
        ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOut));

        new Client(socket).start();

        assertThat(out.toString(), is(String.format("exit%s", System.lineSeparator())));
        assertThat(consoleOut.toString(), is(String.format("some string%s", LS)));
    }
}