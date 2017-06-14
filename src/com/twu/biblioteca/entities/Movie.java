package com.twu.biblioteca.entities;

import java.util.HashMap;

/**
 * Created by alvarohernandez on 6/13/17.
 */
public class Movie implements LibraryElement{

    private int id;
    private String name;
    private String year;
    private String director;
    private int rating;

    public static final String NAME_FIELD = "name";
    public static final String YEAR_FIELD= "year";
    public static final String DIRECTOR_FIELD= "director";
    public static final String RATING_FIELD= "rating";

    public Movie(int id,String name, String year, String director, int rating) {
        this.id = id;
        this.name = name;

        validateYear(year);
        this.year = year;

        this.director = director;

        validateRating(rating);
        this.rating = rating;
    }

    private void validateYear(String year) {
        if(!year.matches("^\\d{4}$")){
            throw new IllegalArgumentException("Year is not a valid four digits string");
        }
    }

    private void validateRating(int rating) {
        if(rating <0 || rating > 5){
            throw new IllegalArgumentException("Rating should be higher that 0 and lower than 5");
        }
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public int getRating() {
        return rating;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getAsSimpleListElement() {
        return String.valueOf(id).concat(". ").concat(name).concat("\n");
    }

    @Override
    public HashMap<String, String> getDetails() {
        HashMap<String, String> details = new HashMap<String, String>();
        details.put(NAME_FIELD,name);
        details.put(YEAR_FIELD,year);
        details.put(DIRECTOR_FIELD,director);
        details.put(RATING_FIELD,String.valueOf(rating));
        return details;
    }
}
