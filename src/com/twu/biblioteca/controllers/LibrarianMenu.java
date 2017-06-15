package com.twu.biblioteca.controllers;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by alvarohernandez on 5/27/17.
 */
public class LibrarianMenu extends LibraryMenu {
    private Scanner reader;

    public LibrarianMenu(LibraryController libraryController) {
        this(System.in,libraryController);
    }

    public LibrarianMenu(InputStream in, LibraryController libraryController) {
        super(in,"Librarian Menu",libraryController);
    }

    @Override
    LibraryMenu loadMenu() {
        addOption(LibraryMenu.BOOKS_LIST_OPTION);
        addOption(LibraryMenu.BOOKS_DETAILS_OPTION);
        addOption(LibraryMenu.BOOK_CHECKOUT_OPTION);
        addOption(LibraryMenu.BOOK_CHECKIN_OPTION);
        addOption(LibraryMenu.MOVIES_LIST_OPTION);
        addOption(LibraryMenu.MOVIES_DETAILS_OPTION);
        addOption(LibraryMenu.MOVIE_CHECKOUT_OPTION);
        addOption(LibraryMenu.MOVIE_CHECKIN_OPTION);
        addOption(LibraryMenu.QUIT_OPTION);
        return this;
    }

}
