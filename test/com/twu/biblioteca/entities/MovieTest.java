package com.twu.biblioteca.entities;

import com.twu.biblioteca.entities.Movie;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by alvarohernandez on 6/13/17.
 */
public class MovieTest {

    private int validId = 1;
    private String validName = "Killswitch";
    private String validYear = "2004";
    private String validDirector = "Ali Akbarzadeh";
    private int validRating = 2;

    @Test
    public void testMovieHasANameAYearADirectorAndARating(){

        Movie movie = new Movie(validId,validName,validYear,validDirector,validRating);

        assertEquals(validName,movie.getName());
        assertEquals(validYear,movie.getYear());
        assertEquals(validDirector,movie.getDirector());
        assertEquals(validRating,movie.getRating());
    }

    @Test
    public void movieWithRatingEqualToZeroCanBeCreated(){
        int rating = 0;
        Movie movie = new Movie(validId,validName,validYear,validDirector,rating);
        assertEquals(rating,movie.getRating());
    }

    @Test
    public void movieWithRatingEqualToFiveCanBeCreated(){
        int rating = 5;
        Movie movie = new Movie(validId,validName,validYear,validDirector,rating);
        assertEquals(rating,movie.getRating());
    }

    @Test(expected=IllegalArgumentException.class)
    public void creatingMovieWithRatingLowerThanZeroShouldThrowAnIllegalArgumentException(){
        int rating = -1;
        Movie movie = new Movie(validId,validName,validYear,validDirector,rating);
    }

    @Test(expected=IllegalArgumentException.class)
    public void creatingMovieWithRatingHigherThanFiveShouldThrowAnIllegalArgumentException(){
        int rating = 6;
        Movie movie = new Movie(validId,validName,validYear,validDirector,rating);
    }

    @Test
    public void movieWithRatingBetweenZeroAndFiveCanBeCreated(){
        int rating = 2;
        Movie movie = new Movie(validId,validName,validYear,validDirector,rating);
        assertEquals(rating,movie.getRating());
    }

    @Test
    public void movieWithValidYearStringCanBeCreated(){
        String year = "2004";
        Movie movie = new Movie(validId,validName,year,validDirector,validRating);
        assertEquals(year,movie.getYear());
    }

    @Test(expected=IllegalArgumentException.class)
    public void movieWithInvalidYearStringShouldThrowAnException(){
        String year = "x2004";
        Movie movie = new Movie(validId,validName,year,validDirector,validRating);
    }
}
