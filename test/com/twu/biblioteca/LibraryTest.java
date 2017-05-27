package com.twu.biblioteca;


import com.twu.biblioteca.entities.Book;
import com.twu.biblioteca.controllers.Catalog;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class LibraryTest {

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
    public void listAvailableBooks() throws Exception {
        Catalog library = new Catalog();
        assertEquals(expectedBookListResult,library.getAvailableBooksListString());

    }

    @Test
    public void bookDetailsHaveAuthorName() throws Exception {
        Catalog library = new Catalog();

        HashMap<String,String> bookDetails1 = library.getBookById(1).getDetails();
        HashMap<String,String> bookDetails2 =   library.getBookById(2).getDetails();

        assertEquals("Sam Newman", bookDetails1.get(Book.AUTHOR_FIELD));
        assertNotEquals("Kent Beck", bookDetails1.get(Book.AUTHOR_FIELD));
        assertEquals("Kent Beck", bookDetails2.get(Book.AUTHOR_FIELD));


    }

    @Test
    public void bookDetailsHaveYearPublished() throws Exception {

        Catalog library = new Catalog();

        HashMap<String,String> bookDetails1 = library.getBookById(1).getDetails();
        HashMap<String,String> bookDetails2 =   library.getBookById(2).getDetails();

        assertEquals("2015", bookDetails1.get(Book.YEAR_PUBLISHED_FIELD));
        assertNotEquals("2000", bookDetails1.get(Book.YEAR_PUBLISHED_FIELD));
        assertEquals("2000", bookDetails2.get(Book.YEAR_PUBLISHED_FIELD));

    }

    @Test
    public void libraryGivesDetailsHeadersAsColumns() throws Exception {

        Catalog library = new Catalog();


        String headers = library.getDetailHeadersAsColumns();
        String expectedHeaders = "title\t\t" + "author\t\t" + "year_published";

        assertEquals(expectedHeaders,headers);
    }


    @Test
    public void getBookDetailsAsColumn() throws Exception {

        Catalog library = new Catalog();

        String headers = library.getDetailedBookDataAsColumnsString();

        assertEquals(expectedBookDetailsResult,headers);

    }

    @Test
    public void availableBookCanBeCheckedOut() throws Exception {

        Catalog library = new Catalog();

        assertEquals("Book 'TDD by Example' was successfully checked-out. Thank you! Enjoy the book",library.checkOutBook(2));
    }

    @Test
    public void checkedOutBookDoesntAppearInAvailableList() throws Exception {
        Catalog library = new Catalog();

        assertEquals(expectedBookListResult,library.getAvailableBooksListString());
        library.checkOutBook(2);
        assertEquals(expectedBookListResultAfterCheckoutBookId2,library.getAvailableBooksListString());
        library.checkOutBook(3);
        assertEquals(expectedBookListResultAfterCheckoutBookId2And3,library.getAvailableBooksListString());
    }

    @Test
    public void errorMessageIsGivenAfterChoosingInvalidBookForCheckout() throws Exception {
        Catalog library = new Catalog();
        
        assertEquals("Invalid Book Selection",library.checkOutBook(-1));
        assertEquals("Invalid Book Selection",library.checkOutBook(4));
        assertEquals(expectedBookListResult,library.getAvailableBooksListString());

    }


    @Test
    public void errorMessageIsGivenAfterChoosingBookForCheckoutTwice() throws Exception {
        Catalog library = new Catalog();
        
        library.checkOutBook(1);
        assertEquals("Invalid Book Selection",library.checkOutBook(1));

    }

    @Test
    public void notAvailableBookCanBeCheckedIn() throws Exception {

        Catalog library = new Catalog();

        library.checkOutBook(2);
        assertEquals("Book 'TDD by Example' was successfully checked-in. Thank you for returning it!",library.checkInBook(2));
    }

    @Test
    public void returnedBookAppearsInAvailableBooksAgain() throws Exception {

        Catalog library = new Catalog();

        library.checkOutBook(2);
        assertEquals(expectedBookListResultAfterCheckoutBookId2,library.getAvailableBooksListString());
        library.checkInBook(2);
        assertEquals(expectedBookListResult,library.getAvailableBooksListString());
    }

    @Test
    public void libraryShowCurrentlyCheckedOutBooks() throws Exception {
        Catalog library = new Catalog();

        library.checkOutBook(3);
        library.checkOutBook(2);

        assertEquals(currentlyCheckedOutBooks,library.getCheckedOutBooksAsRows());

    }


    @Test
    public void errorMessageIsGivenAfterChoosingInvalidBookForCheckIn() throws Exception {

        Catalog library = new Catalog();

        assertEquals("Invalid Book Selection",library.checkInBook(-1));
        assertEquals("Invalid Book Selection",library.checkInBook(4));
    }

    @Test
    public void errorMessageIsGivenAfterChoosingBookForCheckInWhichHasNotBeenCheckedOut() throws Exception {

        Catalog library = new Catalog();

        assertEquals("Invalid Book Selection",library.checkInBook(3));
    }
}
