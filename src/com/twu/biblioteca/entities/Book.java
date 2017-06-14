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

    public Book(String title, Integer id, String author, String yearPublished) {
        this.id = id;
        this.title = title;
        this.author = author;
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

    public HashMap<String, String> getDetails() {
        HashMap<String, String> details = new HashMap<String, String>();
        details.put(TITLE_FIELD,title);
        details.put(AUTHOR_FIELD,author);
        details.put(YEAR_PUBLISHED_FIELD,yearPublished);
        return details;
    }

    @Override
    public String toString(){
        return this.getTitle();
    }

    @Override
    public String getAsSimpleListElement() {
        return String.valueOf(id).concat(". ").concat(title).concat("\n");
    }
}