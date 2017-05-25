package com.twu.biblioteca;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class Library extends BibliotecaApp {

    private static final String welcomeMessage = "Welcome!";
    private boolean ready;

    public String start() {
        ready = true;
        return welcomeMessage;

    }


    public Book[] listAvailableBooks() throws IllegalAccessException {
        if(!ready)
            throw  new IllegalAccessException();

        Book[] books = new Book[3];

        books[0] = new Book("Building Microservices");
        books[1] = new Book("TDD by Example");
        books[2] =new Book( "Head First Java");

        return books;
    }
}
