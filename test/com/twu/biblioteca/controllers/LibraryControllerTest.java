package com.twu.biblioteca.controllers;

import com.twu.biblioteca.presentation.View;
import com.twu.biblioteca.services.Catalog;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by alvarohernandez on 6/14/17.
 */
public class LibraryControllerTest {


    private static final String validUsername = "Alvaro";
    private static final String validPassword = "123";
    @Test
    public void thereIsAUserLoggedAfterLoginWithInvalidCredential(){
        LibraryController controller = new LibraryController(new Catalog(), new View());
        controller.attemptLogin(validUsername,validPassword);
        assertTrue(controller.userIsLogged());
    }

    @Test
    public void thereIsNoUserLoggedAfterLoginWithInvalidCredential(){
        LibraryController controller = new LibraryController(new Catalog(), new View());
        controller.attemptLogin("Alvaro","asd");
        assertFalse(controller.userIsLogged());
        controller.attemptLogin("Claudia","123");
        assertFalse(controller.userIsLogged());
    }

    @Test(expected = IllegalAccessException.class)
    public void checkingOutLibraryWithoutLoggedUserThrowsException() throws IllegalAccessException {
        LibraryController controller = new LibraryController(new Catalog(), new View());
        controller.checkOutBook(1);
    }

    @Test
    public void checkingOutLibraryWithLoggedUserShouldNotThrowException() throws IllegalAccessException {
        LibraryController controller = new LibraryController(new Catalog(), new View());
        controller.attemptLogin(validUsername,validPassword);
        controller.checkOutBook(1);
    }

    @Test(expected = IllegalAccessException.class)
    public void checkingInLibraryWithoutLoggedUserThrowsException() throws IllegalAccessException {
        LibraryController controller = new LibraryController(new Catalog(), new View());
        controller.checkInBook(1);
    }

    @Test
    public void checkingInLibraryWithLoggedUserShouldNotThrowException() throws IllegalAccessException {
        LibraryController controller = new LibraryController(new Catalog(), new View());
        controller.attemptLogin(validUsername,validPassword);
        controller.checkInBook(1);
    }

    @Test
    public void afterBookCheckOutLoggedUsernameShouldBeRegistered() throws IllegalAccessException {
        LibraryController controller = new LibraryController(new Catalog(), new View());
        controller.attemptLogin(validUsername,validPassword);
        controller.checkOutBook(1);
        assertEquals(validUsername,controller.getCheckedOutLibraryElementAssociatedUsername(1));
    }

    @Test
    public void afterBookCheckInLoggedUsernameShouldBeNull() throws IllegalAccessException {
        LibraryController controller = new LibraryController(new Catalog(), new View());
        controller.attemptLogin(validUsername,validPassword);
        controller.checkOutBook(1);
        assertEquals(validUsername,controller.getCheckedOutLibraryElementAssociatedUsername(1));
        controller.checkInBook(1);
        assertNull(controller.getCheckedOutLibraryElementAssociatedUsername(1));
    }

}
