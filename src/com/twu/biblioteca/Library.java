package com.twu.biblioteca;

import java.util.ArrayList;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class Library extends BibliotecaApp {

    private static final String welcomeMessage = "Welcome!";
    private boolean ready;

    private ArrayList<Book> books= new ArrayList<Book>();


    public String start() {
        books.add(new Book("Building Microservices"));
        books.add(new Book("TDD by Example"));
        books.add(new Book( "Head First Java"));
        ready = true;

        return welcomeMessage;

    }


    public ArrayList<Book> getAvailableBooks() throws IllegalAccessException {
        if(!ready)
            throw  new IllegalAccessException();
        return books;
    }


}
