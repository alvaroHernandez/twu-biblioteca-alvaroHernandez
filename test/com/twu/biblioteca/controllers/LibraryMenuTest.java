package com.twu.biblioteca.controllers;

import com.twu.biblioteca.presentation.View;
import com.twu.biblioteca.services.Catalog;
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

        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(new Catalog(),new View()));

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

        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(new Catalog(),new View()));

        libraryMenu.addOption(option1);
        libraryMenu.addOption(option2);

        String expectedList =
                "\n-------------------------------------\n" +
                "Main Menu:"+
                "\n-------------------------------------\n" +
                "1. List Books\n"+
                "2. List Books Details\n" +
                "-------------------------------------\n" +
                "Option:";
        assertEquals(expectedList, libraryMenu.getMenuString());

    }

    @Test
    public void getMenuName() throws Exception {
        LibraryMenu librarianMenu = new LibrarianMenu(new LibraryController(new Catalog(),new View()));

        assertEquals("Librarian Menu", librarianMenu.getName());
    }

    @Test
    public void menuReceiveInvalidCommand() throws Exception {
        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(new Catalog(),new View()));

        libraryMenu.addOption("Option 1");

        assertEquals("Select a valid option!\n", libraryMenu.selectOption("2"));
        assertEquals("Select a valid option!\n", libraryMenu.selectOption("L"));
        assertEquals("Select a valid option!\n", libraryMenu.selectOption(""));
        assertEquals("Select a valid option!\n", libraryMenu.selectOption(null));
        assertEquals("Select a valid option!\n", libraryMenu.selectOption("-1"));
    }

    @Test
    public void menuReceiveValidCommand() throws IllegalAccessException {
        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(new Catalog(),new View()));
        String optionName1 = "Option 1";
        String optionName2 = "Option 2";
        libraryMenu.addOption(optionName1);
        libraryMenu.addOption(optionName2);

        assertEquals(optionName1+" Selected\n", libraryMenu.selectOption("1"));
        assertEquals(optionName2+" Selected\n", libraryMenu.selectOption("2"));
    }



    @Test(expected = IllegalAccessException.class)
    public void menuDoesNotAllowMoreInputAfterQuit() throws Exception {
        LibraryMenu libraryMenu = new LibraryMenu("Main Menu",new LibraryController(new Catalog(),new View()));
        libraryMenu.addOption("Option 1");
        libraryMenu.addOption("Quit");

        libraryMenu.selectOption("1");
        assertFalse(libraryMenu.isClosed());
        assertEquals("Quit Selected\n", libraryMenu.selectOption("2"));
        assertTrue(libraryMenu.isClosed());
        //should throw error
        libraryMenu.selectOption("1");

    }


}
