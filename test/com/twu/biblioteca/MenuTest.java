package com.twu.biblioteca;

import com.twu.biblioteca.Screen.Menu;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class MenuTest {


    @Test
    public void menuHasOptions() throws Exception {

        String option1 = "List Books";
        String option2 = "List Books Details";

        Menu menu = new Menu("Main Menu");

        menu.addOption(option1);
        menu.addOption(option2);

        int index = 0;
        assertEquals(index + 1 +". "+ option1,menu.getOptionString(index++));
        assertEquals(index + 1 +". "+ option2,menu.getOptionString(index++));
    }

    @Test
    public void getMenuName() throws Exception {
        String menuName = "Main Menu";
        Menu menu = new Menu(menuName);

        assertEquals(menuName, menu.getName());
    }

    @Test
    public void menuReceiveInvalidCommand() throws Exception {
        Menu menu = new Menu("Main Menu");

        menu.addOption("Option 1");

        assertEquals("Select a valid option!\n",menu.selectOption("2"));
        assertEquals("Select a valid option!\n",menu.selectOption("L"));
        assertEquals("Select a valid option!\n",menu.selectOption(""));
        assertEquals("Select a valid option!\n",menu.selectOption(null));
        assertEquals("Select a valid option!\n",menu.selectOption("-1"));
    }

    @Test
    public void menuReceiveValidCommand() throws IllegalAccessException {
        Menu menu = new Menu("Main Menu");
        String optionName1 = "Option 1";
        String optionName2 = "Option 2";
        menu.addOption(optionName1);
        menu.addOption(optionName2);

        assertEquals(optionName1+" Selected\n", menu.selectOption("1"));
        assertEquals(optionName2+" Selected\n", menu.selectOption("2"));
    }

    @Test
    public void menuReceiveAndExecuteDisplayBookList() throws Exception {
        Menu menu = new Menu("Main Menu");
        String optionName = "Book List";
        menu.addOption(optionName);

        String result = menu.selectOption("1");

        assertEquals("Book List Selected\n"+
                LibraryTest.expectedBookListResult,result);

    }

    @Test
    public void menuReceiveAndExecuteDisplayDetailedBookList() throws Exception {
        Menu menu = new Menu("Main Menu");
        String optionName = "Book Details";
        menu.addOption(optionName);

        String result = menu.selectOption("1");

        assertEquals("Book Details Selected\n"+
                LibraryTest.expectedBookDetailsResult,result);

    }

    @Test(expected = IllegalAccessException.class)
    public void menuDoesNotAllowMoreInputAfterQuit() throws Exception {
        Menu menu = new Menu("Main Menu");
        menu.addOption("Option 1");
        menu.addOption("Quit");

        menu.selectOption("1");
        assertFalse(menu.isClosed());
        assertEquals("Quit Selected\nBye!",menu.selectOption("2"));
        assertTrue(menu.isClosed());
        //should throw error
        menu.selectOption("1");

    }


}
