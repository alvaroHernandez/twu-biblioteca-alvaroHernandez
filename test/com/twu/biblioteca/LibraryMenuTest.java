package com.twu.biblioteca;

import com.twu.biblioteca.presentation.LibraryMenu;
import com.twu.biblioteca.controller.Catalog;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class LibraryMenuTest {


    @Test
    public void menuHasOptions() throws Exception {

        String option1 = "List Books";
        String option2 = "List Books Details";
        String option3 = "Quit";

        LibraryMenu libraryMenu = new LibraryMenu("Main LibraryMenu", new Catalog());

        libraryMenu.addOption(option1);
        libraryMenu.addOption(option2);
        libraryMenu.addOption(option3);

        int index = 0;
        assertEquals(index + 1 +". "+ option1, libraryMenu.getOptionString(index++));
        assertEquals(index + 1 +". "+ option2, libraryMenu.getOptionString(index++));
        assertEquals(index + 1 +". "+ option3, libraryMenu.getOptionString(index));
    }

    @Test
    public void menuListOptionsAsNumberedList() throws Exception {
        String option1 = "List Books";
        String option2 = "List Books Details";

        LibraryMenu libraryMenu = new LibraryMenu("Main LibraryMenu", new Catalog());

        libraryMenu.addOption(option1);
        libraryMenu.addOption(option2);

        String expectedList =
                "\n-------------------------------------\n" +
                "Main LibraryMenu:"+
                "\n-------------------------------------\n" +
                "1. List Books\n"+
                "2. List Books Details\n" +
                "-------------------------------------\n" +
                "Option:";
        assertEquals(expectedList, libraryMenu.getMenuString());

    }

    @Test
    public void getMenuName() throws Exception {
        String menuName = "Main LibraryMenu";
        LibraryMenu libraryMenu = new LibraryMenu(menuName, new Catalog());

        assertEquals(menuName, libraryMenu.getName());
    }

    @Test
    public void menuReceiveInvalidCommand() throws Exception {
        LibraryMenu libraryMenu = new LibraryMenu("Main LibraryMenu", new Catalog());

        libraryMenu.addOption("Option 1");

        assertEquals("Select a valid option!\n", libraryMenu.selectOption("2"));
        assertEquals("Select a valid option!\n", libraryMenu.selectOption("L"));
        assertEquals("Select a valid option!\n", libraryMenu.selectOption(""));
        assertEquals("Select a valid option!\n", libraryMenu.selectOption(null));
        assertEquals("Select a valid option!\n", libraryMenu.selectOption("-1"));
    }

    @Test
    public void menuReceiveValidCommand() throws IllegalAccessException {
        LibraryMenu libraryMenu = new LibraryMenu("Main LibraryMenu", new Catalog());
        String optionName1 = "Option 1";
        String optionName2 = "Option 2";
        libraryMenu.addOption(optionName1);
        libraryMenu.addOption(optionName2);

        assertEquals(optionName1+" Selected\n", libraryMenu.selectOption("1"));
        assertEquals(optionName2+" Selected\n", libraryMenu.selectOption("2"));
    }

    @Test
    public void menuReceiveAndExecuteDisplayBookList() throws Exception {
        LibraryMenu libraryMenu = new LibraryMenu("Main LibraryMenu", new Catalog());
        libraryMenu.addOption(LibraryMenu.BOOKS_LIST_OPTION);

        String result = libraryMenu.selectOption("1");

        assertEquals(LibraryMenu.BOOKS_LIST_OPTION+" Selected\n"+
                LibraryTest.expectedBookListResult,result);

    }

    @Test
    public void menuReceiveAndExecuteDisplayDetailedBookList() throws Exception {
        LibraryMenu libraryMenu = new LibraryMenu("Main LibraryMenu", new Catalog());
        libraryMenu.addOption(LibraryMenu.BOOKS_DETAILS_OPTION);

        String result = libraryMenu.selectOption("1");

        assertEquals(LibraryMenu.BOOKS_DETAILS_OPTION+" Selected\n"+
                LibraryTest.expectedBookDetailsResult,result);

    }

    @Test
    public void menuDisplayAvailableBooksAndAAskForBookIdAfterChoosingCheckoutOption() throws Exception {
        LibraryMenu libraryMenu = new LibraryMenu("Main LibraryMenu", new Catalog());
        libraryMenu.addOption(LibraryMenu.BOOK_CHECKOUT_OPTION);

        String result = libraryMenu.selectOption("1");

        assertEquals(
                    LibraryMenu.BOOK_CHECKOUT_OPTION +" Selected\n"+
                    LibraryMenu.LISTING_AVAILABLE_BOOKS_MESSAGE+
                    LibraryTest.expectedBookListResult+
                    LibraryMenu.BOOK_CHECKOUT_PROMPT_MESSAGE,

                    result
                );
    }

    @Test
    public void menuDisplayWarningMessageIfThereIsNoBooksToCheckOut() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new LibraryMenu("Main LibraryMenu",catalog);
        libraryMenu.addOption(LibraryMenu.BOOK_CHECKOUT_OPTION);

        catalog.checkOutBook(1);
        catalog.checkOutBook(2);
        catalog.checkOutBook(3);

        assertEquals(LibraryMenu.BOOK_CHECKOUT_OPTION +" Selected\n"+"No Books Available for Check-Out", libraryMenu.selectOption("1"));
    }

    @Test
    public void menuDisplayWarningMessageIfThereIsNoBooksToCheckIn() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new LibraryMenu("Main LibraryMenu",catalog);
        libraryMenu.addOption(LibraryMenu.BOOK_CHECKIN_OPTION);

        assertEquals(LibraryMenu.BOOK_CHECKIN_OPTION +" Selected\n"+"No Books Available for Check-In", libraryMenu.selectOption("1"));

    }

    @Test
    public void menuDisplayCurrentlyCheckedOutBooksAndAAskForBookIdAfterChoosingCheckInOption() throws Exception {
        Catalog catalog = new Catalog();
        LibraryMenu libraryMenu = new LibraryMenu("Main LibraryMenu", catalog);
        libraryMenu.addOption(LibraryMenu.BOOK_CHECKIN_OPTION);

        catalog.checkOutBook(3);
        catalog.checkOutBook(2);

        String result = libraryMenu.selectOption("1");

        assertEquals(
                LibraryMenu.BOOK_CHECKIN_OPTION +" Selected\n"+
                        "Listing Borrowed Books\n"+
                        LibraryTest.currentlyCheckedOutBooks+
                        LibraryMenu.BOOK_CHECKIN_PROMPT_MESSAGE,

                result
        );
    }



    @Test(expected = IllegalAccessException.class)
    public void menuDoesNotAllowMoreInputAfterQuit() throws Exception {
        LibraryMenu libraryMenu = new LibraryMenu("Main LibraryMenu", new Catalog());
        libraryMenu.addOption("Option 1");
        libraryMenu.addOption("Quit");

        libraryMenu.selectOption("1");
        assertFalse(libraryMenu.isClosed());
        assertEquals("Quit Selected\nBye!", libraryMenu.selectOption("2"));
        assertTrue(libraryMenu.isClosed());
        //should throw error
        libraryMenu.selectOption("1");

    }




}
