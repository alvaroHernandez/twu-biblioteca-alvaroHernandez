package com.twu.biblioteca;

import com.twu.biblioteca.presentation.CustomerView;
import com.twu.biblioteca.presentation.View;
import com.twu.biblioteca.controllers.Catalog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class ViewTest {

    static final String expectedWelcomeMessage = "Welcome to Biblioteca!";
    static final String expectedWelcomeErrorMessage = "Catalog has not been loaded yet.";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void customerViewShouldOutputWelcomeMessageAtStart() throws Exception {
        View view = new CustomerView(new Catalog(),false);
        String regexp = "^"+ expectedWelcomeMessage+"\\n[\\s\\S]*$";
        assertTrue(outContent.toString().matches(regexp));
    }

    @Test
    public void showAvailableBooksListAfterWelcomeMessageIfMenuIsDisabled() throws Exception {
        View view = new CustomerView(new Catalog(),false);

        String expectedOutput =  expectedWelcomeMessage+"\n" +
                                "Listing available books:\n\n" +
                                "1. Building Microservices" + "\n" +
                                "2. TDD by Example" + "\n" +
                                "3. Head First Java" + "\n\n" +
                                "There are no more books to show.\n";

        if (!view.isMenuEnabled()) {
            assertEquals(expectedOutput, outContent.toString());
        }else{
            assertNotEquals(expectedOutput, outContent.toString());
        }
    }


    /* Testing interactivity */
    /* I'm not sure how (if I should) to test interactivity */

    /*
    @Test
    public void showMainMenuIfItsEnabled() throws Exception {
        View printer = new View(new Catalog(),true);

        String regexp =  "^"+ expectedWelcomeMessage+"\\n" +
                "Main LibraryMenu:\\n"+
                "1\\.[\\s\\S]*$";

        System.err.println(outContent.toString());
        System.err.println(regexp);
        if (printer.isMenuEnabled()){
            assertTrue(outContent.toString().matches(regexp));
        }else{
            assertFalse(outContent.toString().matches(regexp));
        }
    }*/

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }
}
