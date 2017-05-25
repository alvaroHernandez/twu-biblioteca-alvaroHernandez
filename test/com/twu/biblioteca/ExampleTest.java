package com.twu.biblioteca;


import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ExampleTest {

    @Test
    public void welcomeMessageShouldAppearAtFirst() {
        Library library = new Library();

        assertEquals("Welcome!", library.start());
    }

    @Test
    public void listAvailableBooks() throws Exception {
        Library library = new Library();

        library.start();
        Book[] books  = library.listAvailableBooks();

        assertEquals("Building Microservices",books[0].getName());
        assertEquals("TDD by Example",books[1].getName());
        assertEquals("Head First Java",books[2].getName());

    }

    @Test(expected=IllegalAccessException.class)
    public void shouldListAvailableBooksOnlyIfLibraryIsStarted() throws Exception {
        Library library = new Library();

        //Should throw exception
        library.listAvailableBooks();
    }

    
}
