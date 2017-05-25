package com.twu.biblioteca;


import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ExampleTest {

    @Test
    public void shouldWelcomeMessageAppearAtFirst() {
        Library library = new Library();

        assertEquals("Welcome!", library.start());
    }

    @Test
    public void listAvailableBooks() throws Exception {
        Library library = new Library();

        library.start();
        ArrayList<Book> books  = library.getAvailableBooks();

        assertEquals("Building Microservices",books.get(0).getName());
        assertEquals("TDD by Example",books.get(1).getName());
        assertEquals("Head First Java",books.get(2).getName());

    }

    @Test(expected=IllegalAccessException.class)
    public void shouldListAvailableBooksOnlyIfLibraryIsStarted() throws Exception {
        Library library = new Library();

        //Should throw exception
        library.getAvailableBooks();
    }


}
