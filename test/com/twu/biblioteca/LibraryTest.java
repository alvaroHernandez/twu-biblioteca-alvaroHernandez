package com.twu.biblioteca;


import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class LibraryTest {

    static final String expectedWelcomeMessage = "Welcome!";

    static final String expectedBookListResult  =
            "1. Building Microservices\n"+
            "2. TDD by Example\n"+
            "3. Head First Java\n";

    static final String expectedBookDetailsResult  =
            "title                     author        year_published    \n"+
            "----------------------------------------------------------\n"+
            "Building Microservices    Sam Newman    2015              \n"+
            "TDD by Example            Kent Beck     2000              \n"+
            "Head First Java           Bert Bates    2003              \n";

    @Test
    public void shouldWelcomeMessageAppearAtFirst() {
        Library library = new Library();
        assertEquals(expectedWelcomeMessage, library.start());
    }

    @Test
    public void listAvailableBooks() throws Exception {
        Library library = new Library();

        library.start();
        ArrayList<Library.Book> books  = library.getAvailableBooks();

        assertEquals("Building Microservices",books.get(0).getTitle());
        assertEquals("TDD by Example",books.get(1).getTitle());
        assertEquals("Head First Java",books.get(2).getTitle());

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
        ArrayList<Library.Book> books  = library.getAvailableBooks();

        assertEquals(1,books.get(0).getId());
        assertEquals(2,books.get(1).getId());
        assertEquals(3,books.get(2).getId());

    }

    @Test
    public void bookDetailsHaveAuthorName() throws Exception {
        Library library = new Library();

        library.start();
        ArrayList<Library.Book> books  = library.getAvailableBooks();

        HashMap<String,String> bookDetailsZero = books.get(0).getDetails();
        HashMap<String,String> bookDetailsOne = books.get(1).getDetails();

        assertEquals("Sam Newman", bookDetailsZero.get(Library.Book.AUTHOR_FIELD));
        assertNotEquals("Kent Beck", bookDetailsZero.get(Library.Book.AUTHOR_FIELD));
        assertEquals("Kent Beck", bookDetailsOne.get(Library.Book.AUTHOR_FIELD));


    }

    @Test
    public void bookDetailsHaveYearPublished() throws Exception {

        Library library = new Library();

        library.start();
        ArrayList<Library.Book> books  = library.getAvailableBooks();

        HashMap<String,String> bookDetailsZero = books.get(0).getDetails();
        HashMap<String,String> bookDetailsOne = books.get(1).getDetails();

        assertEquals("2015", bookDetailsZero.get(Library.Book.YEAR_PUBLISHED_FIELD));
        assertNotEquals("2000", bookDetailsZero.get(Library.Book.YEAR_PUBLISHED_FIELD));
        assertEquals("2000", bookDetailsOne.get(Library.Book.YEAR_PUBLISHED_FIELD));

    }

    @Test
    public void libraryGivesDetailsHeadersAsColumns() throws Exception {

        Library library = new Library();

        library.start();

        String headers = library.getDetailHeadersAsColumns();
        String expectedHeaders = "title\t\t" + "author\t\t" + "year_published";

        assertEquals(expectedHeaders,headers);
    }


    @Test
    public void getBookDetailsAsColumn() throws Exception {

        Library library = new Library();
        library.start();

        String headers = library.getDetailedBookDataAsColumns();

        assertEquals(expectedBookDetailsResult,headers);

    }

    @Test
    public void availableBookCanBeCheckedOut() throws Exception {

        Library library = new Library();
        library.start();

        assertEquals("Book 'TDD by Example' was successfully checked-out",library.checkOutBook(2));

    }
}
