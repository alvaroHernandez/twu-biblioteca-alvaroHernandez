package com.twu.biblioteca.services;


import com.twu.biblioteca.entities.Book;
import com.twu.biblioteca.entities.User;
import org.junit.Test;

import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class CatalogTest {


    //BOOKS TEST EXPECTED RESULT

    public static final String expectedBookListResult  =
            "1. Building Microservices\n"+
            "2. TDD by Example\n"+
            "3. Head First Java\n";

    public static final String expectedMovieListResult  =
            "1. Killswitch\n"+
            "2. Wonder Woman\n" +
            "3. Logan\n";

    public static final String expectedBookListResultAfterCheckoutBookId2  =
            "1. Building Microservices\n"+
            "3. Head First Java\n";

    public static final String expectedBookListResultAfterCheckoutBookId2And3  =
            "1. Building Microservices\n";

    public static final String currentlyCheckedOutBooks =
            "2. TDD by Example - CheckedOutBy: Alvaro\n" +
            "3. Head First Java - CheckedOutBy: Claudia\n";

    public static final String currentlyCheckedOutMovies =
            "2. Wonder Woman - CheckedOutBy: Claudia\n" +
            "3. Logan - CheckedOutBy: Alvaro\n";

    public static final String expectedBookDetailsResult  =
            "title                     author        year_published    \n"+
            "----------------------------------------------------------\n"+
            "Building Microservices    Sam Newman    2015              \n"+
            "TDD by Example            Kent Beck     2000              \n"+
            "Head First Java           Bert Bates    2003              \n";



    //MOVIES TEST EXPECTED RESULT

    public static final String expectedBookMoviesResult  =
            "1. Killswitch\n"+
            "2. Wonder Woman\n"+
            "3. Logan\n";


    public static final String expectedMoviesDetailsResult  =
            "name            year    director          rating    \n"+
            "----------------------------------------------------\n"+
            "Killswitch      2004    Ali Akbarzadeh    3         \n"+
            "Wonder Woman    2017    Patty Jenkins     5         \n"+
            "Logan           2017    James Mangold     4         \n";


    private TreeMap<Integer,Book> defaultAvailableBooks() {
        TreeMap<Integer,Book> availableBooks = new TreeMap<Integer,Book>();
        availableBooks.put(1,new Book("Building Microservices",1,"Sam Newman", "2015"));
        availableBooks.put(2,new Book("TDD by Example",2,"Kent Beck", "2000"));
        availableBooks.put(3,new Book( "Head First Java",3,"Bert Bates", "2003"));
        return availableBooks;
    }

    @Test
    public void listAvailableBooks() throws Exception {
        Catalog library = new Catalog();
        assertEquals(defaultAvailableBooks().toString(),library.getAvailableBooks().toString());

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
    public void availableBookCanBeCheckedOut() throws Exception {

        Catalog library = new Catalog();
        User loggedUser = new User("Alvaro","123","alvaro@tw.com","Chile","123123");
        assertEquals("Book 'TDD by Example' was successfully checked-out. Thank you! Enjoy the book",library.checkOutBook(loggedUser.getName(), 2));
    }

    @Test
    public void checkedOutBookDoesntAppearInAvailableList() throws Exception {
        TreeMap<Integer,Book> availableBooks = defaultAvailableBooks();

        Catalog catalog= new Catalog();

        assertEquals(availableBooks.toString(),catalog.getAvailableBooks().toString());
        User loggedUser = new User("Alvaro","123","alvaro@tw.com","Chile","123123");
        catalog.checkOutBook(loggedUser.getName(), 2);
        availableBooks.remove(2);

        assertEquals(availableBooks.toString(),catalog.getAvailableBooks().toString());
        catalog.checkOutBook(loggedUser.getName(), 3);

        availableBooks.remove(3);
        assertEquals(availableBooks.toString(),catalog.getAvailableBooks().toString());
    }


    @Test
    public void errorMessageIsGivenAfterChoosingInvalidBookForCheckout() throws Exception {
        Catalog catalog = new Catalog();
        User loggedUser = new User("Alvaro","123","alvaro@tw.com","Chile","123123");
        assertEquals("Invalid Book Selection",catalog.checkOutBook(loggedUser.getName(), -1));
        assertEquals("Invalid Book Selection",catalog.checkOutBook(loggedUser.getName(), 4));

    }


    @Test
    public void errorMessageIsGivenAfterChoosingBookForCheckoutTwice() throws Exception {
        Catalog library = new Catalog();
        User loggedUser = new User("Alvaro","123","alvaro@tw.com","Chile","123123");
        library.checkOutBook(loggedUser.getName(), 1);
        assertEquals("Invalid Book Selection",library.checkOutBook(loggedUser.getName(), 1));

    }

    @Test
    public void notAvailableBookCanBeCheckedIn() throws Exception {

        Catalog library = new Catalog();
        User loggedUser = new User("Alvaro","123","alvaro@tw.com","Chile","123123");
        library.checkOutBook(loggedUser.getName(), 2);
        assertEquals("Book 'TDD by Example' was successfully checked-in. Thank you for returning it!",library.checkInBook("Alvaro",2));
    }

    @Test
    public void returnedBookAppearsInAvailableBooksAgain() throws Exception {
        TreeMap<Integer,Book> availableBooks = defaultAvailableBooks();
        Catalog catalog = new Catalog();
        User loggedUser = new User("Alvaro","123","alvaro@tw.com","Chile","123123");
        catalog.checkOutBook(loggedUser.getName(), 2);
        availableBooks.remove(2);
        assertEquals(availableBooks.toString(),catalog.getAvailableBooks().toString());
        catalog.checkInBook("Alvaro",2);
        assertEquals(defaultAvailableBooks().toString(),catalog.getAvailableBooks().toString());
    }


    @Test
    public void errorMessageIsGivenAfterChoosingInvalidBookForCheckIn() throws Exception {

        Catalog library = new Catalog();

        assertEquals("Invalid Book Selection",library.checkInBook("Alvaro",-1));
        assertEquals("Invalid Book Selection",library.checkInBook("Alvaro",4));
    }

    @Test
    public void errorMessageIsGivenAfterChoosingBookForCheckInWhichHasNotBeenCheckedOut() throws Exception {

        Catalog library = new Catalog();

        assertEquals("Invalid Book Selection",library.checkInBook("Alvaro",3));
    }
}
