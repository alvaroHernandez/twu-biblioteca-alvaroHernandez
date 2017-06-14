package com.twu.biblioteca.controllers;

/**
 * Created by alvarohernandez on 5/27/17.
 */
public class CustomerMenu extends LibraryMenu {


    public CustomerMenu(LibraryController libraryController) {
        super("Customer Menu",libraryController);
    }

    @Override
    LibraryMenu loadMenu() {
        addOption(LibraryMenu.BOOKS_LIST_OPTION);
        addOption(LibraryMenu.BOOKS_DETAILS_OPTION);
        addOption(LibraryMenu.MOVIES_LIST_OPTION);
        addOption(LibraryMenu.QUIT_OPTION);
        return this;
    }


}
