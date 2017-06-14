package com.twu.biblioteca.controllers;

import com.twu.biblioteca.presentation.View;
import com.twu.biblioteca.services.Catalog;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class LibraryController {

    private static final String welcomeMessage = "Welcome to Biblioteca!";
    private Catalog catalog;
    private View view;


    public LibraryController(Catalog catalog, View view) {
        this.catalog = catalog;
        this.view = view;
        view.printWelcomeMessage(getWelcomeMessage());

    }

    private String getWelcomeMessage(){
        //library is ready just when a catalog is initialized
        if(catalog!=null){
            return welcomeMessage;
        }
        return null;
    }

    public boolean thereAreAvailableBooksForCheckOut() {
        return catalog.thereAreAvailableBooksForCheckOut();
    }

    public boolean thereAreAvailableBooksForCheckIn() {
        return catalog.thereAreAvailableBooksForCheckIn();
    }

    public void getAvailableBooks() {
        view.printAvailableBooks(catalog.getAvailableBooks());
    }

    public void getAvailableBooksListString() {
        view.printLibraryElementListAsRows(catalog.getAvailableBooks());
    }

    public void getCheckedOutBooksAsRows() {
        view.printLibraryElementListAsRows(catalog.getCheckedOutBooks());
    }

    public void getDetailedBookDataAsColumnsString(){
        view.printDetailedBookDataAsColumnsString(catalog.getAvailableBooks());
    }

    public void getDetailedMovieDataAsColumnsString(){
        view.printDetailedMovieDataAsColumnsString(catalog.getAvailableMovies());
    }

    public void getAvailableMoviesListString() {
        view.printLibraryElementListAsRows(catalog.getAvailableMovies());
    }

    public void sendMessage(String message) {
        view.printMessage(message);
    }

    public void checkOutBook(int value) {
        view.printMessage(catalog.checkOutBook(value));
    }

    public void checkInBook(int value) {
        view.printMessage(catalog.checkInBook(value));
    }
}
