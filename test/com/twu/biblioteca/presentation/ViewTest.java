package com.twu.biblioteca.presentation;

import com.twu.biblioteca.controllers.CustomerMenu;
import com.twu.biblioteca.controllers.LibraryController;
import com.twu.biblioteca.controllers.LibraryMenu;
import com.twu.biblioteca.services.Catalog;
import com.twu.biblioteca.services.CatalogTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;

import static com.twu.biblioteca.services.CatalogTest.currentlyCheckedOutBooks;
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
    public void customerViewShouldOutputWelcomeMessageAtStart() throws Exception {
        View view = new View();
        LibraryController controller = new LibraryController(new Catalog(),view);
        String regexp = "^"+ expectedWelcomeMessage+"\\n[\\s\\S]*$";
        assertTrue(outContent.toString().matches(regexp));
    }

    @Test
    public void showAvailableBooksListAfterWelcomeMessage() throws Exception {
        View view = new View();
        LibraryController controller = new LibraryController(new Catalog(),view);
        controller.getAvailableBooks();

        String expectedOutput =  expectedWelcomeMessage+"\n" +
                                "Listing available books:\n\n" +
                                "1. Building Microservices" + "\n" +
                                "2. TDD by Example" + "\n" +
                                "3. Head First Java" + "\n\n" +
                                "There are no more books to show.\n";

        assertEquals(expectedOutput, outContent.toString());

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
    public void libraryShowCurrentlyCheckedOutBooks() throws Exception {
        View view = new View();
        Catalog catalog = new Catalog();

        catalog.checkOutBook(3);
        catalog.checkOutBook(2);

        view.printLibraryElementListAsRows(catalog.getCheckedOutBooks());

        assertEquals(currentlyCheckedOutBooks+"\n",outContent.toString());

    }

    @Test
    public void menuDisplayWarningMessageIfThereIsNoBooksToCheckOut() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new CustomerMenu(new LibraryController(catalog,new View()));
        libraryMenu.addOption(LibraryMenu.BOOK_CHECKOUT_OPTION);

        catalog.checkOutBook(1);
        catalog.checkOutBook(2);
        catalog.checkOutBook(3);
        String expectedResult =
                expectedWelcomeMessage + "\n" +
                LibraryMenu.BOOK_CHECKOUT_OPTION + " Selected\n\n"+"No Books Available for Check-Out\n";

        libraryMenu.selectOption("1");
        assertEquals(expectedResult, outContent.toString());
    }

    @Test
    public void menuDisplayAvailableBooksAndAAskForBookIdAfterChoosingCheckoutOption() throws Exception {
        LibraryMenu libraryMenu = new CustomerMenu(new LibraryController(new Catalog(),new View()));
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
    public void menuDisplayWarningMessageIfThereIsNoBooksToCheckIn() throws Exception {
        LibraryMenu libraryMenu = new CustomerMenu(new LibraryController(new Catalog(),new View()));
        libraryMenu.addOption(LibraryMenu.BOOK_CHECKIN_OPTION);

        String result = libraryMenu.selectOption("1");

        assertEquals(
                expectedWelcomeMessage + "\n" +
                        LibraryMenu.BOOK_CHECKIN_OPTION +" Selected\n\n"+
                        "No Books Available for Check-In\n",

                        outContent.toString());

    }

    @Test
    public void menuDisplayCurrentlyCheckedOutBooksAndAAskForBookIdAfterChoosingCheckInOption() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new CustomerMenu(new LibraryController(catalog,new View()));
        libraryMenu.addOption(LibraryMenu.BOOK_CHECKIN_OPTION);

        catalog.checkOutBook(3);
        catalog.checkOutBook(2);

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
    public void menuReceiveAndExecuteDisplayMovieList() throws Exception {
        LibraryMenu libraryMenu = new CustomerMenu(new LibraryController(new Catalog(),new View()));
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
    public void menuReceiveAndExecuteDisplayDetailedMovieList() throws Exception {
        LibraryMenu libraryMenu = new CustomerMenu(new LibraryController(new Catalog(),new View()));
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
    public void menuReceiveAndExecuteDisplayDetailedBookList() throws Exception {
        LibraryMenu libraryMenu = new CustomerMenu(new LibraryController(new Catalog(),new View()));
        libraryMenu.addOption(LibraryMenu.BOOKS_DETAILS_OPTION);

        String result = libraryMenu.selectOption("1");

        assertEquals(
                expectedWelcomeMessage + "\n" +
                LibraryMenu.BOOKS_DETAILS_OPTION+" Selected\n\n"+
                CatalogTest.expectedBookDetailsResult+"\n",outContent.toString());

    }

    @Test
    public void menuReceiveAndExecuteDisplayBookList() throws Exception {
        LibraryMenu libraryMenu = new CustomerMenu(new LibraryController(new Catalog(),new View()));
        libraryMenu.addOption(LibraryMenu.BOOKS_LIST_OPTION);

        String result = libraryMenu.selectOption("1");

        assertEquals(
                expectedWelcomeMessage + "\n" +
                LibraryMenu.BOOKS_LIST_OPTION+" Selected\n\n"+
                CatalogTest.expectedBookListResult+"\n",outContent.toString());

    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }
}
