package com.twu.biblioteca;

import java.util.ArrayList;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class Screen {

    Library library = new Library();

    public Screen() throws IllegalAccessException {
        System.out.println(library.start());
        listAvailableBooks();
    }

    public void listAvailableBooks() throws IllegalAccessException {
        System.out.println("Listing available books:\n");

        ArrayList<Book> availableBooks = library.getAvailableBooks();
        for (Book availableBook : availableBooks) {
            System.out.println(availableBook.getId() + ". " + availableBook.getName());
        }
        System.out.println();
        System.out.println("There are no more books to show.");

    }
}
