package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.*;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class ScreenTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        //System.setErr(new PrintStream(errContent));
    }

    @Test
    public void screenShouldOutputWelcomeMessageAtStart() throws Exception {
        Screen screen = new Screen();
        String regexp = "^"+LibraryTest.expectedWelcomeMessage+"\\n[\\s\\S]*$";
        assertTrue(outContent.toString().matches(regexp));
    }

    @Test
    public void showAvailableBooksListAfterWelcomeMessageIfMenuIsDisabled() throws Exception {
        Screen screen = new Screen();

        String expectedOutput =  LibraryTest.expectedWelcomeMessage+"\n" +
                                "Listing available books:\n\n" +
                                "1. Building Microservices" + "\n" +
                                "2. TDD by Example" + "\n" +
                                "3. Head First Java" + "\n\n" +
                                "There are no more books to show.\n";

        if (!screen.isMenuEnabled()) {
            assertEquals(expectedOutput, outContent.toString());
        }else{
            assertNotEquals(expectedOutput, outContent.toString());
        }
    }

    @Test
    public void showMainMenuIfItsEnabled() throws Exception {
        Screen screen = new Screen();

        String regexp =  "^"+LibraryTest.expectedWelcomeMessage+"\\n" +
                "Main Menu:\\n"+
                "1\\.[\\s\\S]*$";

        if (screen.isMenuEnabled()){
            assertTrue(outContent.toString().matches(regexp));
        }else{
            assertFalse(outContent.toString().matches(regexp));
        }
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        //System.setErr(null);
    }
}
