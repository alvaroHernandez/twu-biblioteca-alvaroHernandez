package com.twu.biblioteca.Screen;

import com.twu.biblioteca.library.Shelf;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class Printer {

    Shelf library = new Shelf();
    private boolean menuEnabled = true;

    public Printer() throws IllegalAccessException {
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
