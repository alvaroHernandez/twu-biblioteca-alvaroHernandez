package com.twu.biblioteca.controllers;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by alvarohernandez on 5/27/17.
 */
public class CustomerMenu extends LibraryMenu {


    public CustomerMenu(LibraryController libraryController) {
        this(System.in,libraryController);
    }

    public CustomerMenu(InputStream in,LibraryController libraryController) {
        super(in,"Customer Menu",libraryController);
        promptForUsername();
    }

    private void promptForUsername() {
        Scanner reader = new Scanner(System.in);
        while(!libraryController.userIsLogged()){
            libraryController.sendMessage("Username:");
            String username = reader.nextLine();
            libraryController.sendMessage("Password:");
            String password = reader.nextLine();
            libraryController.attemptLogin(username,password);
        }
    }



    @Override
    LibraryMenu loadMenu() {
        addOption(LibraryMenu.BOOKS_LIST_OPTION);
        addOption(LibraryMenu.BOOKS_DETAILS_OPTION);
        addOption(LibraryMenu.MOVIES_LIST_OPTION);
        addOption(LibraryMenu.MOVIES_DETAILS_OPTION);
        addOption(LibraryMenu.QUIT_OPTION);
        return this;
    }


}
