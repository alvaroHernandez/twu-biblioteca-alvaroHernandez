package com.twu.biblioteca.entities;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class Book implements LibraryElement{

    public static final String TITLE_FIELD = "title";
    public static final String AUTHOR_FIELD= "author";
    public static final String YEAR_PUBLISHED_FIELD= "year_published";

    private int id;
    private String title;
    private String author;
    private String yearPublished;

    private String currentOwner;

    public Book(String title, Integer id, String author, String yearPublished) {
        this.id = id;
        this.title = title;
        this.author = author;
        validateYear(yearPublished);
        this.yearPublished = yearPublished;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getYearPublished() {
        return yearPublished;
    }

    private void validateYear(String year) {
        if(!year.matches("^\\d{4}$")){
            throw new IllegalArgumentException("Year is not a valid four digits string");
        }
    }

    public HashMap<String, String> getDetails() {
        HashMap<String, String> details = new HashMap<String, String>();
        details.put(TITLE_FIELD,title);
        details.put(AUTHOR_FIELD,author);
        details.put(YEAR_PUBLISHED_FIELD,yearPublished);
        return details;
    }

    @Override
    public String getCurrentOwner() {
        return currentOwner;
    }

    @Override
    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
    }

    @Override
    public String toString(){
        return this.getTitle();
    }

    @Override
    public String getAsSimpleListElement() {
        StringBuilder elementString = new StringBuilder();
        elementString.append(id).append(". ").append(title);
        if(currentOwner != null){
            elementString.append(" - CheckedOutBy: ").append(currentOwner);
        }
        elementString.append("\n");
        return  elementString.toString();
    }
}