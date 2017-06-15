package com.twu.biblioteca.services;

import com.twu.biblioteca.entities.Book;
import com.twu.biblioteca.entities.Movie;

import java.util.*;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class Catalog {

    //treeMap in order to index id (in ascending order)
    private TreeMap<Integer,Book> availableBooks = new TreeMap<Integer,Book>();
    private TreeMap<Integer,Movie> availableMovies = new TreeMap<Integer,Movie>();
    private TreeMap<Integer,Book> checkedOutBooks= new TreeMap<Integer,Book>();
    private TreeMap<Integer,Movie> checkedOutMovies= new TreeMap<Integer,Movie>();

    public Catalog() {
        loadElements();
    }

    private void loadElements() {
        loadBooks();
        loadMovies();
    }

    public TreeMap<Integer, Book> getAvailableBooks() {
        return availableBooks;
    }

    public TreeMap<Integer, Movie> getAvailableMovies() {
        return availableMovies;
    }

    public TreeMap<Integer, Book> getCheckedOutBooks() {
        return checkedOutBooks;
    }

    public TreeMap<Integer,Movie> getCheckedOutMovies() {
        return checkedOutMovies;
    }

    private void loadBooks() {
        availableBooks.put(1,new Book("Building Microservices",1,"Sam Newman", "2015"));
        availableBooks.put(2,new Book("TDD by Example",2,"Kent Beck", "2000"));
        availableBooks.put(3,new Book( "Head First Java",3,"Bert Bates", "2003"));
    }

    private void loadMovies(){
        availableMovies.put(1,new Movie(1,"Killswitch","2004","Ali Akbarzadeh",3));
        availableMovies.put(2,new Movie(2,"Wonder Woman","2017","Patty Jenkins",5));
        availableMovies.put(3,new Movie(3, "Logan","2017","James Mangold",4));
    }

    public Book getBookById(int id) {
        return availableBooks.get(id);
    }


    public boolean thereAreAvailableBooksForCheckOut() {
        return !availableBooks.isEmpty();
    }

    public boolean thereAreAvailableMoviesForCheckOut() {
        return !availableMovies.isEmpty();
    }

    public boolean thereAreAvailableBooksForCheckIn() {
        return !checkedOutBooks.isEmpty();
    }

    public boolean thereAreAvailableMoviesForCheckIn() {
        return !checkedOutMovies.isEmpty();
    }

    public String checkOutBook(String userName, int id) {

        Book book = availableBooks.get(id);
        if(book!= null){
            book.setCurrentOwner(userName);
            checkedOutBooks.put(id,book);
            //responsabilidad unica
            availableBooks.remove(id);
            return  "Book '"+ book.getTitle() + "' was successfully checked-out. Thank you! Enjoy the book";
        }else{
            return "Invalid Book Selection"; //devlover boolean
        }
    }

    public String checkOutMovie(String userName, int id) {
        Movie movie = availableMovies.get(id);
        if(movie!= null){
            movie.setCurrentOwner(userName);
            checkedOutMovies.put(id,movie);
            //responsabilidad unica
            availableMovies.remove(id);
            return  "Movie '"+ movie.getName() + "' was successfully checked-out. Thank you! Enjoy the movie";
        }else{
            return "Invalid Movie Selection"; //devlover boolean
        }
    }

    public String checkInBook(String username, int id) {

        Book book = checkedOutBooks.get(id);
        if(book!= null && username.equals(book.getCurrentOwner())){
            book.setCurrentOwner(null);
            availableBooks.put(id,book);
            checkedOutBooks.remove(id);
            return  "Book '"+ book.getTitle() + "' was successfully checked-in. Thank you for returning it!";
        }else{
            return "Invalid Book Selection";
        }
    }

    public String checkInMovie(String username, int id) {

        Movie movie = checkedOutMovies.get(id);
        if(movie!= null && username.equals(movie.getCurrentOwner())){
            movie.setCurrentOwner(null);
            availableMovies.put(id,movie);
            checkedOutMovies.remove(id);
            return  "Movie '"+ movie.getName() + "' was successfully checked-in. Thank you for returning it!";
        }else{
            return "Invalid Movie Selection";
        }
    }


    public Book getCheckedOutBookById(int id) {
        return checkedOutBooks.get(id);
    }

    public Movie getCheckedOutMovieAssociatedUsername(int id) {
        return checkedOutMovies.get(id);
    }
}
