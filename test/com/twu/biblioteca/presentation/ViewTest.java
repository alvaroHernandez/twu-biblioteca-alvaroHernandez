package com.twu.biblioteca.presentation;

import com.twu.biblioteca.controllers.LibrarianMenu;
import com.twu.biblioteca.controllers.LibraryController;
import com.twu.biblioteca.controllers.LibraryMenu;
import com.twu.biblioteca.entities.User;
import com.twu.biblioteca.services.Catalog;
import com.twu.biblioteca.services.CatalogTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static com.twu.biblioteca.services.CatalogTest.currentlyCheckedOutBooks;
import static com.twu.biblioteca.services.CatalogTest.currentlyCheckedOutMovies;
import static com.twu.biblioteca.services.CatalogTest.expectedBookDetailsResult;
import static org.junit.Assert.*;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class ViewTest {

    static final String expectedWelcomeMessage = "Welcome to Biblioteca!";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();


    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }


    @Test
    public void viewShouldOutputWelcomeMessageAtStart() throws Exception {
        View view = new View();
        LibraryController controller = new LibraryController(new Catalog(),view);
        String regexp = "^"+ expectedWelcomeMessage+"\\n[\\s\\S]*$";
        assertTrue(outContent.toString().matches(regexp));
    }

    @Test
    public void libraryGivesDetailsHeadersAsColumns() throws Exception {

        View view = new View();

        String headers = view.getDetailHeadersAsColumns();
        String expectedHeaders = "title\t\t" + "author\t\t" + "year_published";

        assertEquals(expectedHeaders,headers);
    }


    @Test
    public void getBookDetailsAsColumn() throws Exception {

        View view = new View();

        view.printDetailedBookDataAsColumnsString(new Catalog().getAvailableBooks());

        assertEquals(expectedBookDetailsResult+"\n",outContent.toString());

    }

    @Test
    public void menuReceiveAndExecuteDisplayDetailedBookList() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(catalog,new View()));
        libraryMenu.addOption(LibraryMenu.BOOKS_DETAILS_OPTION);

        String result = libraryMenu.selectOption("1");

        assertEquals(
                expectedWelcomeMessage + "\n" +
                        LibraryMenu.BOOKS_DETAILS_OPTION+" Selected\n\n"+
                        CatalogTest.expectedBookDetailsResult+"\n",outContent.toString());

    }

    @Test
    public void menuReceiveAndExecuteDisplayDetailedMovieList() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(catalog,new View()));
        libraryMenu.addOption(LibraryMenu.MOVIES_DETAILS_OPTION);

        String result = libraryMenu.selectOption("1");

        assertEquals(
                expectedWelcomeMessage + "\n" +
                        LibraryMenu.MOVIES_DETAILS_OPTION+" Selected\n\n"+
                        CatalogTest.expectedMoviesDetailsResult+"\n"
                ,
                outContent.toString());

    }

    @Test
    public void menuReceiveAndExecuteDisplayBookList() throws Exception {
        LibraryMenu libraryMenu = new LibrarianMenu(new LibraryController(new Catalog(),new View()));
        libraryMenu.addOption(LibraryMenu.BOOKS_LIST_OPTION);

        String result = libraryMenu.selectOption("1");

        assertEquals(
                expectedWelcomeMessage + "\n" +
                        LibraryMenu.BOOKS_LIST_OPTION+" Selected\n\n"+
                        CatalogTest.expectedBookListResult+"\n",outContent.toString());

    }

    @Test
    public void menuReceiveAndExecuteDisplayMovieList() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(catalog,new View()));
        libraryMenu.addOption(LibraryMenu.MOVIES_LIST_OPTION);

        String result = libraryMenu.selectOption("1");

        assertEquals(
                expectedWelcomeMessage + "\n" +
                        LibraryMenu.MOVIES_LIST_OPTION+" Selected\n\n"+
                        CatalogTest.expectedBookMoviesResult+"\n"
                ,
                outContent.toString());
    }


    @Test
    public void libraryShowCurrentlyCheckedOutBooks() throws Exception {
        View view = new View();
        Catalog catalog = new Catalog();
        catalog.checkOutBook("Alvaro", 2);
        catalog.checkOutBook("Claudia", 3);

        view.printLibraryElementListAsRows(catalog.getCheckedOutBooks());

        assertEquals(currentlyCheckedOutBooks+"\n",outContent.toString());

    }

    @Test
    public void libraryShowCurrentlyCheckedOutMovies() throws Exception {
        View view = new View();
        Catalog catalog = new Catalog();

        catalog.checkOutMovie("Alvaro",3);
        catalog.checkOutMovie("Claudia",2);

        view.printLibraryElementListAsRows(catalog.getCheckedOutMovies());

        assertEquals(currentlyCheckedOutMovies+"\n",outContent.toString());

    }

    @Test
    public void menuDisplayWarningMessageIfThereIsNoBooksToCheckOut() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(catalog,new View()));
        libraryMenu.addOption(LibraryMenu.BOOK_CHECKOUT_OPTION);
        User loggedUser = new User("Alvaro","123","alvaro@tw.com","Chile","123123");
        catalog.checkOutBook(loggedUser.getName(), 1);
        catalog.checkOutBook(loggedUser.getName(), 2);
        catalog.checkOutBook(loggedUser.getName(), 3);
        String expectedResult =
                expectedWelcomeMessage + "\n" +
                LibraryMenu.BOOK_CHECKOUT_OPTION + " Selected\n\n"+"No Books Available for Check-Out\n";

        libraryMenu.selectOption("1");
        assertEquals(expectedResult, outContent.toString());
    }

    @Test
    public void menuDisplayWarningMessageIfThereIsNoMoviesToCheckOut() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(catalog,new View()));
        libraryMenu.addOption(LibraryMenu.MOVIE_CHECKOUT_OPTION);

        catalog.checkOutMovie("Alvaro",1);
        catalog.checkOutMovie("Alvaro",2);
        catalog.checkOutMovie("Alvaro",3);

        String expectedResult =
                expectedWelcomeMessage + "\n" +
                LibraryMenu.MOVIE_CHECKOUT_OPTION + " Selected\n\n"+"No Movies Available for Check-Out\n";

        libraryMenu.selectOption("1");
        assertEquals(expectedResult, outContent.toString());
    }

    @Test
    public void menuDisplayAvailableBooksAndAAskForBookIdAfterChoosingCheckoutOption() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(catalog,new View()));
        libraryMenu.addOption(LibraryMenu.BOOK_CHECKOUT_OPTION);

        String result = libraryMenu.selectOption("1");

        assertEquals(
                expectedWelcomeMessage + "\n" +
                        LibraryMenu.BOOK_CHECKOUT_OPTION +" Selected\n"+
                        "\n" +
                        LibraryMenu.LISTING_AVAILABLE_BOOKS_MESSAGE+"\n"+
                        CatalogTest.expectedBookListResult+"\n"+
                        LibraryMenu.BOOK_CHECKOUT_PROMPT_MESSAGE+"\n",

                outContent.toString()
        );
    }

    @Test
    public void menuDisplayAvailableBooksAndAAskForMovieIdAfterChoosingCheckoutOption() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(catalog,new View()));
        libraryMenu.addOption(LibraryMenu.MOVIE_CHECKOUT_OPTION);

        String result = libraryMenu.selectOption("1");

        assertEquals(
                expectedWelcomeMessage + "\n" +
                        LibraryMenu.MOVIE_CHECKOUT_OPTION +" Selected\n"+
                        "\n" +
                        LibraryMenu.LISTING_AVAILABLE_MOVIES_MESSAGE+"\n"+
                        CatalogTest.expectedMovieListResult+"\n"+
                        LibraryMenu.MOVIE_CHECKOUT_PROMPT_MESSAGE+"\n",

                outContent.toString()
        );
    }


    @Test
    public void menuDisplayWarningMessageIfThereIsNoBooksToCheckIn() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(catalog,new View()));
        libraryMenu.addOption(LibraryMenu.BOOK_CHECKIN_OPTION);

        String result = libraryMenu.selectOption("1");

        assertEquals(
                expectedWelcomeMessage + "\n" +
                        LibraryMenu.BOOK_CHECKIN_OPTION +" Selected\n\n"+
                        "No Books Available for Check-In\n",

                        outContent.toString());

    }

    @Test
    public void menuDisplayWarningMessageIfThereIsNoMoviesToCheckIn() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(catalog,new View()));
        libraryMenu.addOption(LibraryMenu.MOVIE_CHECKIN_OPTION);

        String result = libraryMenu.selectOption("1");

        assertEquals(
                expectedWelcomeMessage + "\n" +
                        LibraryMenu.MOVIE_CHECKIN_OPTION +" Selected\n\n"+
                        "No Movies Available for Check-In\n",

                outContent.toString());

    }

    @Test
    public void menuDisplayCurrentlyCheckedOutBooksAndAAskForBookIdAfterChoosingCheckInOption() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(catalog,new View()));
        libraryMenu.addOption(LibraryMenu.BOOK_CHECKIN_OPTION);
        catalog.checkOutBook("Alvaro", 2);
        catalog.checkOutBook("Claudia", 3);

        String result = libraryMenu.selectOption("1");

        assertEquals(
                expectedWelcomeMessage + "\n" +
                        LibraryMenu.BOOK_CHECKIN_OPTION +" Selected\n\n"+
                        "Listing Borrowed Books\n\n"+
                        CatalogTest.currentlyCheckedOutBooks+"\n"+
                        LibraryMenu.BOOK_CHECKIN_PROMPT_MESSAGE+"\n",

                outContent.toString()
        );
    }

    @Test
    public void menuDisplayCurrentlyCheckedOutMoviesAndAAskForBookIdAfterChoosingCheckInOption() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(catalog,new View()));
        libraryMenu.addOption(LibraryMenu.MOVIE_CHECKIN_OPTION);

        catalog.checkOutMovie("Alvaro",3);
        catalog.checkOutMovie("Claudia",2);

        String result = libraryMenu.selectOption("1");

        assertEquals(
                expectedWelcomeMessage + "\n" +
                        LibraryMenu.MOVIE_CHECKIN_OPTION +" Selected\n\n"+
                        "Listing Borrowed Movies\n\n"+
                        currentlyCheckedOutMovies+"\n"+
                        LibraryMenu.MOVIE_CHECKIN_PROMPT_MESSAGE+"\n",

                outContent.toString()
        );
    }


    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }
}
