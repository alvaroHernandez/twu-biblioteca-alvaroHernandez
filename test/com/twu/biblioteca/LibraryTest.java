package com.twu.biblioteca;


import com.twu.biblioteca.library.Book;
import com.twu.biblioteca.library.Shelf;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class LibraryTest {

    static final String expectedWelcomeMessage = "Welcome!";

    static final String expectedBookListResult  =
            "1. Building Microservices\n"+
            "2. TDD by Example\n"+
            "3. Head First Java\n";

    static final String expectedBookListResultAfterCheckoutBookId2  =
            "1. Building Microservices\n"+
            "3. Head First Java\n";

    static final String expectedBookListResultAfterCheckoutBookId2And3  =
            "1. Building Microservices\n";

    static final String currentlyCheckedOutBooks =
            "2. TDD by Example\n" +
            "3. Head First Java\n";

    static final String expectedBookDetailsResult  =
            "title                     author        year_published    \n"+
            "----------------------------------------------------------\n"+
            "Building Microservices    Sam Newman    2015              \n"+
            "TDD by Example            Kent Beck     2000              \n"+
            "Head First Java           Bert Bates    2003              \n";

    @Test
    public void shouldWelcomeMessageAppearAtFirst() {
        Shelf library = new Shelf();
        assertEquals(expectedWelcomeMessage, library.start());
    }


    @Test
    public void listAvailableBooks() throws Exception {
        Shelf library = new Shelf();

        library.start();
        assertEquals(expectedBookListResult,library.getAvailableBooksAsRows());

    }

    @Test(expected=IllegalAccessException.class)
    public void shouldListAvailableBooksOnlyIfLibraryIsStarted() throws Exception {
        Shelf library = new Shelf();

        //Should throw exception
        library.getAvailableBooks();
    }

    @Test
    public void bookDetailsHaveAuthorName() throws Exception {
        Shelf library = new Shelf();
        library.start();

        HashMap<String,String> bookDetails1 = library.getBookById(1).getDetails();
        HashMap<String,String> bookDetails2 =   library.getBookById(2).getDetails();

        assertEquals("Sam Newman", bookDetails1.get(Book.AUTHOR_FIELD));
        assertNotEquals("Kent Beck", bookDetails1.get(Book.AUTHOR_FIELD));
        assertEquals("Kent Beck", bookDetails2.get(Book.AUTHOR_FIELD));


    }

    @Test
    public void bookDetailsHaveYearPublished() throws Exception {

        Shelf library = new Shelf();
        library.start();

        HashMap<String,String> bookDetails1 = library.getBookById(1).getDetails();
        HashMap<String,String> bookDetails2 =   library.getBookById(2).getDetails();

        assertEquals("2015", bookDetails1.get(Book.YEAR_PUBLISHED_FIELD));
        assertNotEquals("2000", bookDetails1.get(Book.YEAR_PUBLISHED_FIELD));
        assertEquals("2000", bookDetails2.get(Book.YEAR_PUBLISHED_FIELD));

    }

    @Test
    public void libraryGivesDetailsHeadersAsColumns() throws Exception {

        Shelf library = new Shelf();

        library.start();

        String headers = library.getDetailHeadersAsColumns();
        String expectedHeaders = "title\t\t" + "author\t\t" + "year_published";

        assertEquals(expectedHeaders,headers);
    }


    @Test
    public void getBookDetailsAsColumn() throws Exception {

        Shelf library = new Shelf();
        library.start();

        String headers = library.getDetailedBookDataAsColumns();

        assertEquals(expectedBookDetailsResult,headers);

    }

    @Test
    public void availableBookCanBeCheckedOut() throws Exception {

        Shelf library = new Shelf();
        library.start();

        assertEquals("Book 'TDD by Example' was successfully checked-out. Thank you! Enjoy the book",library.checkOutBook(2));
    }

    @Test
    public void checkedOutBookDoesntAppearInAvailableList() throws Exception {
        Shelf library = new Shelf();
        library.start();

        assertEquals(expectedBookListResult,library.getAvailableBooksAsRows());
        library.checkOutBook(2);
        assertEquals(expectedBookListResultAfterCheckoutBookId2,library.getAvailableBooksAsRows());
        library.checkOutBook(3);
        assertEquals(expectedBookListResultAfterCheckoutBookId2And3,library.getAvailableBooksAsRows());
    }

    @Test
    public void errorMessageIsGivenAfterChoosingInvalidBookForCheckout() throws Exception {
        Shelf library = new Shelf();
        library.start();

        assertEquals("Invalid Book Selection",library.checkOutBook(-1));
        assertEquals("Invalid Book Selection",library.checkOutBook(4));
        assertEquals(expectedBookListResult,library.getAvailableBooksAsRows());

    }


    @Test
    public void errorMessageIsGivenAfterChoosingBookForCheckoutTwice() throws Exception {
        Shelf library = new Shelf();
        library.start();

        library.checkOutBook(1);
        assertEquals("Invalid Book Selection",library.checkOutBook(1));

    }

    @Test
    public void notAvailableBookCanBeCheckedIn() throws Exception {

        Shelf library = new Shelf();
        library.start();

        library.checkOutBook(2);
        assertEquals("Book 'TDD by Example' was successfully checked-in. Thank you for returning it!",library.checkInBook(2));
    }

    @Test
    public void returnedBookApearsInAvailableBooksAgain() throws Exception {

        Shelf library = new Shelf();
        library.start();

        library.checkOutBook(2);
        assertEquals(expectedBookListResultAfterCheckoutBookId2,library.getAvailableBooksAsRows());
        library.checkInBook(2);
        assertEquals(expectedBookListResult,library.getAvailableBooksAsRows());
    }

    @Test
    public void libraryShowCurrentlyCheckedOutBooks() throws Exception {
        Shelf library = new Shelf();
        library.start();

        library.checkOutBook(3);
        library.checkOutBook(2);

        assertEquals(currentlyCheckedOutBooks,library.getCheckedOutBooksAsRows());

    }


    @Test
    public void errorMessageIsGivenAfterChoosingInvalidBookForCheckIn() throws Exception {

        Shelf library = new Shelf();
        library.start();

        assertEquals("Invalid Book Selection",library.checkInBook(-1));
        assertEquals("Invalid Book Selection",library.checkInBook(4));
    }

    @Test
    public void errorMessageIsGivenAfterChoosingBookForCheckInWhichHasNotBeenCheckedOut() throws Exception {

        Shelf library = new Shelf();
        library.start();

        assertEquals("Invalid Book Selection",library.checkInBook(3));
    }
}
