package com.twu.biblioteca.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by alvarohernandez on 6/13/17.
 */
public class BookTest {

    private int validId = 1;
    private String validTitle = "Building Microservices";
    private String validAuthor = "Sam Newman";
    private String validYearPublished = "2015";


    @Test
    public void bookHasATitleAnAuthorAndAYearPublished(){

        Book book = new Book(validTitle,1,validAuthor, validYearPublished);

        assertEquals(validTitle,book.getTitle());
        assertEquals(validAuthor,book.getAuthor());
        assertEquals(validYearPublished,book.getYearPublished());

    }

    @Test
    public void bookWithValidYearStringCanBeCreated(){
        String year = "2004";
        Book book = new Book(validTitle,1,validAuthor,year);
        assertEquals(year,book.getYearPublished());
    }

    @Test(expected=IllegalArgumentException.class)
    public void bookWithInvalidYearStringShouldThrowAnException(){
        String year = "x2004";
        Book book = new Book(validTitle,1,validAuthor,year);
    }
}
