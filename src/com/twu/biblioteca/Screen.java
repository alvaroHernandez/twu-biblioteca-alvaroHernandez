package com.twu.biblioteca;

import java.util.ArrayList;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class Screen {

    Library library = new Library();
    private boolean menuEnabled = true;

    public Screen() throws IllegalAccessException {
        System.out.println(library.start());

        if(isMenuEnabled()){
            Menu menu = new Menu("Main Menu");
            menu.addOption("1. List Books");
            System.out.println(menu.getMenuString());
        }else{
            listAvailableBooks();
        }

    }

    public void listAvailableBooks() throws IllegalAccessException {
        System.out.println("Listing available books:\n");

        System.out.println(library.getAvailableBooksAsRows());
        System.out.println();
        System.out.println("There are no more books to show.");

    }

    public boolean isMenuEnabled() {
        return menuEnabled;
    }
}
