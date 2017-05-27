package com.twu.biblioteca.presentation;

import com.twu.biblioteca.controller.Catalog;

import java.util.Scanner;

/**
 * Created by alvarohernandez on 5/27/17.
 */
public class LibrarianView extends View {
    Scanner reader;

    public LibrarianView(Catalog catalog, boolean menuEnabled) {
        super(catalog, menuEnabled);
    }

    @Override
    LibraryMenu loadMenu() {
        LibraryMenu libraryMenu = new LibraryMenu("Customer LibraryMenu",this.catalog);
        libraryMenu.addOption(LibraryMenu.BOOKS_LIST_OPTION);
        libraryMenu.addOption(LibraryMenu.BOOKS_DETAILS_OPTION);
        libraryMenu.addOption(LibraryMenu.BOOK_CHECKOUT_OPTION);
        libraryMenu.addOption(LibraryMenu.BOOK_CHECKIN_OPTION);
        libraryMenu.addOption(LibraryMenu.QUIT_OPTION);
        return libraryMenu;
    }


    public void checkIfIsWaitingForBookID(String result){
        if(result.contains(LibraryMenu.BOOK_CHECKOUT_PROMPT_MESSAGE)){
            String input = reader.nextLine();
            int bookId = Integer.parseInt(input);
            result = catalog.checkOutBook(bookId);
            System.out.println(result);
        }else if (result.contains(LibraryMenu.BOOK_CHECKOUT_PROMPT_MESSAGE)){
            String input = reader.nextLine();
            int bookId = Integer.parseInt(input);
            result = catalog.checkInBook(bookId);
            System.out.println(result);
        }
    }


}
