package com.twu.biblioteca.controllers;

import com.twu.biblioteca.entities.LibraryElement;
import com.twu.biblioteca.entities.User;
import com.twu.biblioteca.presentation.View;
import com.twu.biblioteca.services.Catalog;

import java.util.HashMap;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class LibraryController {

    private static final String welcomeMessage = "Welcome to Biblioteca!";
    private Catalog catalog;
    private View view;
    private HashMap<String,User> users = new HashMap<String, User>();
    private User loggedUser;

    public LibraryController(Catalog catalog, View view) {
        this.catalog = catalog;
        this.view = view;
        loadUsersDatabase();
        view.printWelcomeMessage(getWelcomeMessage());
    }

    public void loadUsersDatabase(){
        users.put("Alvaro",new User("Alvaro","123","alvaro@tw.com","Chile","123123"));
        users.put("Claudia",new User("Claudia","asd","claudia@tw.com","Ecuador","44444"));
    }

    public void attemptLogin(String username, String password) {
        if(users.get(username) != null && users.get(username).passwordIs(password)){
            loggedUser = users.get(username);
        }
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

    public boolean thereAreAvailableMoviesForCheckOut() {
        return catalog.thereAreAvailableMoviesForCheckOut();
    }

    public boolean thereAreAvailableBooksForCheckIn() {
        return catalog.thereAreAvailableBooksForCheckIn();
    }

    public boolean thereAreAvailableMoviesForCheckIn() {
        return catalog.thereAreAvailableMoviesForCheckIn();
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

    public void getCheckedOutMoviesAsRows() {
        view.printLibraryElementListAsRows(catalog.getCheckedOutMovies());
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

    public void checkOutBook(int value) throws IllegalAccessException {
        if(!userIsLogged()){
            throw  new IllegalAccessException("You have to be logged before to checkOut a book");
        }
        view.printMessage(catalog.checkOutBook(loggedUser.getName(),value));
    }

    public void checkInBook(int value) throws IllegalAccessException {
        if(!userIsLogged()){
            throw  new IllegalAccessException("You have to be logged before to checkIn a book");
        }
        view.printMessage(catalog.checkInBook(loggedUser.getName(),value));
    }

    public void checkOutMovie(int value) throws IllegalAccessException {
        if(!userIsLogged()){
            throw  new IllegalAccessException("You have to be logged before to checkOut a movie");
        }
        view.printMessage(catalog.checkOutMovie(loggedUser.getName(),value));
    }

    public void checkInMovie(int value) throws IllegalAccessException {
        if(!userIsLogged()){
            throw  new IllegalAccessException("You have to be logged before to checkIn a movie");
        }
        view.printMessage(catalog.checkInMovie(loggedUser.getName(),value));
    }

    public boolean userIsLogged() {
        return !(loggedUser == null);
    }

    public String getCheckedOutLibraryElementAssociatedUsername(int id) {
        LibraryElement libraryElement = catalog.getCheckedOutBookById(id);
        if(libraryElement != null)
            return libraryElement.getCurrentOwner();
        return null;
    }

    public void getUserData() {
        view.printUserData(loggedUser);
    }
}
