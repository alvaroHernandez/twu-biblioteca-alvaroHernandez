package com.twu.biblioteca.presentation;

import com.twu.biblioteca.entities.Book;
import com.twu.biblioteca.entities.LibraryElement;
import com.twu.biblioteca.entities.Movie;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class View {


    public static LinkedHashMap<String,Integer> getBookFieldsHeadersLength(){
        LinkedHashMap<String,Integer> lengths = new LinkedHashMap<String, Integer>();

        lengths.put(Book.TITLE_FIELD, Book.TITLE_FIELD.length());
        lengths.put(Book.AUTHOR_FIELD, Book.AUTHOR_FIELD.length());
        lengths.put(Book.YEAR_PUBLISHED_FIELD, Book.YEAR_PUBLISHED_FIELD.length());
        return lengths;
    }

    public static LinkedHashMap<String,Integer> getMovieFieldsHeadersLength() {
        LinkedHashMap<String,Integer> lengths = new LinkedHashMap<String, Integer>();

        lengths.put(Movie.NAME_FIELD, Movie.NAME_FIELD.length());
        lengths.put(Movie.YEAR_FIELD, Movie.YEAR_FIELD.length());
        lengths.put(Movie.DIRECTOR_FIELD, Movie.DIRECTOR_FIELD.length());
        lengths.put(Movie.RATING_FIELD, Movie.RATING_FIELD.length());
        return lengths;

    }

    public void printWelcomeMessage(String welcomeMessage){
        System.out.println(welcomeMessage);
    }


    public void printAvailableBooks(TreeMap<Integer, ? extends LibraryElement> database) {
        System.out.println("Listing available books:\n");
        printLibraryElementListAsRows(database);
        System.out.println("There are no more books to show.");
    }
    public void printLibraryElementListAsRows(TreeMap<Integer, ? extends LibraryElement> database) {
        System.out.println(getLibraryElementListAsRows(database));
    }

    public String getLibraryElementListAsRows(TreeMap<Integer, ? extends LibraryElement> database){
        StringBuilder listString = new StringBuilder();
        //builds list (rows) with each book: its id + title
        for (LibraryElement availableLibraryElement : database.values()) {
            listString.append(availableLibraryElement.getAsSimpleListElement());
        }
        return listString.toString();
    }
    public void printDetailedMovieDataAsColumnsString(TreeMap<Integer,Movie> availableMovies) {
        //get the longest text for each field in order to calculate spacing
        LinkedHashMap<String,Integer> maxLengths = calculateMaxLengthForEachFieldInMovies(availableMovies);
        printDetailedLibraryElementDataAsColumnsString(availableMovies, maxLengths);
    }

    public void printDetailedBookDataAsColumnsString(TreeMap<Integer, Book> availableBooks) {
        //get the longest text for each field in order to calculate spacing
        LinkedHashMap<String,Integer> maxLengths = calculateMaxLengthForEachFieldInBooks(availableBooks);
        printDetailedLibraryElementDataAsColumnsString(availableBooks, maxLengths);
    }

    public void printDetailedLibraryElementDataAsColumnsString(TreeMap<Integer, ? extends LibraryElement> availableLibraryElements, LinkedHashMap<String, Integer> maxLengths) {


        StringBuilder dataAsColumns = new StringBuilder();

        //append formatted data and separator to result string
        appendHeaders(dataAsColumns,availableLibraryElements,maxLengths);
        appendDashes(dataAsColumns,availableLibraryElements);
        appendLibraryElementData(dataAsColumns,availableLibraryElements,maxLengths);

        System.out.println(dataAsColumns.toString());
    }

    public String getDetailHeadersAsColumns() {
        return String.format("%s\t\t" + "%s\t\t" + "%s",Book.TITLE_FIELD,Book.AUTHOR_FIELD,Book.YEAR_PUBLISHED_FIELD);
    }

    public LinkedHashMap<String,Integer> calculateMaxLengthForEachFieldInBooks(TreeMap<Integer,Book> availableBooks) {
        return calculateMaxLengthForEachField(availableBooks,View.getBookFieldsHeadersLength());
    }

    public LinkedHashMap<String,Integer> calculateMaxLengthForEachFieldInMovies(TreeMap<Integer,Movie> availableMovies) {
        return calculateMaxLengthForEachField(availableMovies,View.getMovieFieldsHeadersLength());
    }

    public LinkedHashMap<String,Integer> calculateMaxLengthForEachField(TreeMap<Integer,? extends LibraryElement> availableLibraryElements,LinkedHashMap<String,Integer> maxLengths ){

        for (LibraryElement libraryElement : availableLibraryElements.values()) {
            HashMap<String,String> details = libraryElement.getDetails();
            //updates the max length register for each field, with the longest value for each book
            for (String field : maxLengths.keySet()) {
                if ( details.get(field).length() > maxLengths.get(field)){
                    maxLengths.put(field,details.get(field).length());
                }
            }
        }
        return maxLengths;
    }


    private void appendLibraryElementData(StringBuilder dataAsColumns,TreeMap<Integer,? extends LibraryElement> availableLibraryElements, LinkedHashMap<String, Integer> maxLengths) {
        for (LibraryElement libraryElement : availableLibraryElements.values()) {
            HashMap<String,String> details = libraryElement.getDetails();
            appendDataWithWhitespaces(dataAsColumns, availableLibraryElements,maxLengths, details);
        }
    }

    private void appendHeaders(StringBuilder dataAsColumns,TreeMap<Integer,? extends LibraryElement> availableLibraryElement, LinkedHashMap<String, Integer> maxLengths) {
        appendDataWithWhitespaces(dataAsColumns,availableLibraryElement,maxLengths,null);
    }

    private void appendDashes(StringBuilder currentString,TreeMap<Integer,? extends LibraryElement> availableLibraryElements) {
        int currentLength = currentString.length();
        for (int i = 0; i < currentLength-1; i++) {
            currentString.append("-");
        }
        currentString.append("\n");
    }

    private void appendDataWithWhitespaces(StringBuilder dataAsColumns,TreeMap<Integer,? extends LibraryElement> availableLibraryElements, LinkedHashMap<String, Integer> maxLengths, HashMap<String, String> details) {
        for (String field : maxLengths.keySet()) {
            int fieldLength;

            if(details==null){
                //append field name to the current string
                dataAsColumns.append(field);
                fieldLength = field.length();
            }else{
                //append field value to the current string
                dataAsColumns.append(details.get(field));
                fieldLength = details.get(field).length();
            }

            //fill with withespaces the remaining spaces between current field length and the max field length
            while(fieldLength < (maxLengths.get(field)+4) ){
                dataAsColumns.append(" ");
                fieldLength++;
            }

        }
        dataAsColumns.append("\n");
    }


    public void printMessage(String message) {
        System.out.println(message);
    }
}
