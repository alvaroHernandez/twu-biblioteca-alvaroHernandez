package com.twu.biblioteca.services;

import com.twu.biblioteca.entities.Book;
import com.twu.biblioteca.entities.LibraryElement;
import com.twu.biblioteca.entities.Movie;
import com.twu.biblioteca.presentation.View;

import java.util.*;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class Catalog {

    //treeMap in order to index id (in ascending order)
    private TreeMap<Integer,Book> availableBooks = new TreeMap<Integer,Book>();
    private TreeMap<Integer,Movie> availableMovies = new TreeMap<Integer,Movie>();
    private TreeMap<Integer,Book> checkedOutBooks= new TreeMap<Integer,Book>();


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

    public String checkOutBook(int id) {

        Book book = availableBooks.get(id);
        if(book!= null){
            checkedOutBooks.put(id,book);
            //responsabilidad unica
            availableBooks.remove(id);
            return  "Book '"+ book.getTitle() + "' was successfully checked-out. Thank you! Enjoy the book";
        }else{
            return "Invalid Book Selection"; //devlover boolean
        }
    }

    public String checkInBook(int id) {

        Book book = checkedOutBooks.get(id);
        if(book!= null){
            availableBooks.put(id,book);
            checkedOutBooks.remove(id);
            return  "Book '"+ book.getTitle() + "' was successfully checked-in. Thank you for returning it!";
        }else{
            return "Invalid Book Selection";
        }
    }

    public boolean thereAreAvailableBooksForCheckOut() {
        return !availableBooks.isEmpty();
    }

    public boolean thereAreAvailableBooksForCheckIn() {
        return !checkedOutBooks.isEmpty();
    }

}
