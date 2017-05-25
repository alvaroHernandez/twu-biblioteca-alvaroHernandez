package com.twu.biblioteca;

import java.util.HashMap;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class Book {

    private int id;
    private HashMap<String, String> details;

    public static final String NAME_FIELD = "name";
    public static final String AUTHOR_FIELD= "author";
    public static final String YEAR_PUBLISHED_FIELD= "year_published";

    public Book(String name, Integer id, String author, String yearPublished) {
        this.id = id;

        this.details = new HashMap<String, String>();

        details.put(NAME_FIELD,name);
        details.put(AUTHOR_FIELD,author);
        details.put(YEAR_PUBLISHED_FIELD,yearPublished);
    }

    public String getName() {
        return details.get(NAME_FIELD);
    }

    public int getId() {
        return id;
    }

    public HashMap<String, String> getDetails() {
        return details;
    }
}
