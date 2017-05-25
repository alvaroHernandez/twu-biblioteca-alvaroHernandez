package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class ScreenTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void screenShouldOutputWelcomeMessageAtStart() throws Exception {
        Screen screen = new Screen();
        String regexp = "^"+LibraryTest.expectedWelcomeMessage+"\\n[\\s\\S]*$";
        assertTrue(outContent.toString().matches(regexp));
    }

    @Test
    public void showAvailableBooksListAfterWelcomeMessage() throws Exception {
        Screen screen = new Screen();

        String expectedOutput =  LibraryTest.expectedWelcomeMessage+"\n" +
                                "Listing available books:\n\n" +
                                "1. Building Microservices" + "\n" +
                                "2. TDD by Example" + "\n" +
                                "3. Head First Java" + "\n\n" +
                                "There are no more books to show.\n";

        assertEquals(expectedOutput, outContent.toString());
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }
}
