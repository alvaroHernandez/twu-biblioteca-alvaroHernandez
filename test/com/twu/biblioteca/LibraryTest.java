package com.twu.biblioteca;


import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class LibraryTest {

    static final String expectedWelcomeMessage = "Welcome!";

    @Test
    public void shouldWelcomeMessageAppearAtFirst() {
        Library library = new Library();
        assertEquals(expectedWelcomeMessage, library.start());
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



    @Test
    public void booksInLibraryHasIncrementalId() throws Exception {

        Library library = new Library();

        library.start();
        ArrayList<Book> books  = library.getAvailableBooks();

        assertEquals(1,books.get(0).getId());
        assertEquals(2,books.get(1).getId());
        assertEquals(3,books.get(2).getId());

    }

    @Test
    public void bookDetailsHaveAuthorName() throws Exception {
        Library library = new Library();

        library.start();
        ArrayList<Book> books  = library.getAvailableBooks();

        HashMap<String,String> bookDetailsZero = books.get(0).getDetails();
        HashMap<String,String> bookDetailsOne = books.get(1).getDetails();

        assertEquals("Sam Newman", bookDetailsZero.get(Book.AUTHOR_FIELD));
        assertNotEquals("Kent Beck", bookDetailsZero.get(Book.AUTHOR_FIELD));
        assertEquals("Kent Beck", bookDetailsOne.get(Book.AUTHOR_FIELD));


    }

    @Test
    public void bookDetailsHaveYearPublished() throws Exception {

        Library library = new Library();

        library.start();
        ArrayList<Book> books  = library.getAvailableBooks();

        HashMap<String,String> bookDetailsZero = books.get(0).getDetails();
        HashMap<String,String> bookDetailsOne = books.get(1).getDetails();

        assertEquals("2015", bookDetailsZero.get(Book.YEAR_PUBLISHED_FIELD));
        assertNotEquals("2000", bookDetailsZero.get(Book.YEAR_PUBLISHED_FIELD));
        assertEquals("2000", bookDetailsOne.get(Book.YEAR_PUBLISHED_FIELD));


    }
}
