package com.twu.biblioteca.library;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class Book {

    private int id;
    private HashMap<String, String> details;

    public static final String TITLE_FIELD = "title";
    public static final String AUTHOR_FIELD= "author";
    public static final String YEAR_PUBLISHED_FIELD= "year_published";


    public Book(String name, Integer id, String author, String yearPublished) {
        this.id = id;

        this.details = new HashMap<String, String>();

        details.put(TITLE_FIELD,name);
        details.put(AUTHOR_FIELD,author);
        details.put(YEAR_PUBLISHED_FIELD,yearPublished);
    }

    public String getTitle() {
        return details.get(TITLE_FIELD);
    }

    public int getId() {
        return id;
    }

    public HashMap<String, String> getDetails() {
        return details;
    }

    public static LinkedHashMap<String,Integer> getFieldsHeadersLength(){
        LinkedHashMap<String,Integer> lengths = new LinkedHashMap<String, Integer>();

        lengths.put(TITLE_FIELD,TITLE_FIELD.length());
        lengths.put(AUTHOR_FIELD,AUTHOR_FIELD.length());
        lengths.put(YEAR_PUBLISHED_FIELD,YEAR_PUBLISHED_FIELD.length());
        return lengths;
    }

    @Override
    public String toString(){
        return this.getTitle();
    }
}