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
        books.add(new Book("Building Microservices",1,"Sam Newman", "2015"));
        books.add(new Book("TDD by Example",2,"Kent Beck", "2000"));
        books.add(new Book( "Head First Java",3,"Bert Bates", "2003"));
        ready = true;

        return welcomeMessage;

    }

    public ArrayList<Book> getAvailableBooks() throws IllegalAccessException {
        if(!ready)
            throw  new IllegalAccessException();
        return books;
    }




}
