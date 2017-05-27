package com.twu.biblioteca.controllers;

import com.twu.biblioteca.entities.Book;

import java.util.*;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class Catalog {

    //treeMap in order to index id (in ascending order)
    private TreeMap<Integer,Book> availableBooks = new TreeMap<Integer,Book>();
    private TreeMap<Integer,Book> checkedOutBooks= new TreeMap<Integer,Book>();


    public Catalog() {
        loadBooks();
    }

    private void loadBooks() {
        availableBooks.put(1,new Book("Building Microservices",1,"Sam Newman", "2015"));
        availableBooks.put(2,new Book("TDD by Example",2,"Kent Beck", "2000"));
        availableBooks.put(3,new Book( "Head First Java",3,"Bert Bates", "2003"));
    }

    public Book getBookById(int id) {
        return availableBooks.get(id);
    }

    private String getBooksListAsRows(TreeMap<Integer,Book> database){
        StringBuilder result = new StringBuilder();
        //builds list (rows) with each book: its id + title
        for (Book availableBook : database.values()) {
            result.append(availableBook.getId()).append(". ").append(availableBook.getTitle()).append("\n");
        }
        return result.toString();

    }

    public String getAvailableBooksListString() {
        return getBooksListAsRows(availableBooks);
    }

    public String getCheckedOutBooksAsRows() {
        return getBooksListAsRows(checkedOutBooks);
    }


    public String getDetailedBookDataAsColumnsString() {
        //get the longest text for each field in order to calculate spacing
        LinkedHashMap<String,Integer> maxLengths = calculateMaxLengthForEachField();

        StringBuilder dataAsColumns = new StringBuilder();

        //append formatted data and separator to result string
        appendHeaders(dataAsColumns,maxLengths);
        appendDashes(dataAsColumns);
        appendBookData(dataAsColumns,maxLengths);

        return dataAsColumns.toString();
    }

    public String getDetailHeadersAsColumns() {
        return String.format("%s\t\t" + "%s\t\t" + "%s",Book.TITLE_FIELD,Book.AUTHOR_FIELD,Book.YEAR_PUBLISHED_FIELD);
    }

    public LinkedHashMap<String,Integer> calculateMaxLengthForEachField(){
        LinkedHashMap<String,Integer> maxLengths = Book.getFieldsHeadersLength();

        for (Book book : availableBooks.values()) {
            HashMap<String,String> details = book.getDetails();
            //updates the max length register for each field, with the longest value for each book
            for (String field : maxLengths.keySet()) {
                if ( details.get(field).length() > maxLengths.get(field)){
                    maxLengths.put(field,details.get(field).length());
                }
            }
        }
        return maxLengths;
    }


    private void appendBookData(StringBuilder dataAsColumns, LinkedHashMap<String, Integer> maxLengths) {
        for (Book book : availableBooks.values()) {
            HashMap<String,String> details = book.getDetails();
            appendDataWithWhitespaces(dataAsColumns, maxLengths, details);
        }
    }

    private void appendHeaders(StringBuilder dataAsColumns, LinkedHashMap<String, Integer> maxLengths) {
        appendDataWithWhitespaces(dataAsColumns,maxLengths,null);
    }

    private void appendDashes(StringBuilder currentString) {
        int currentLength = currentString.length();
        for (int i = 0; i < currentLength-1; i++) {
            currentString.append("-");
        }
        currentString.append("\n");
    }

    private void appendDataWithWhitespaces(StringBuilder dataAsColumns, LinkedHashMap<String, Integer> maxLengths, HashMap<String, String> details) {
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


    public String checkOutBook(int id) {

        Book book = availableBooks.get(id);
        if(book!= null){
            checkedOutBooks.put(id,book);
            availableBooks.remove(id);
            return  "Book '"+ book.getTitle() + "' was successfully checked-out. Thank you! Enjoy the book";
        }else{
            return "Invalid Book Selection";
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

    public boolean thereAreAvailableBooksForCheckout() {
        return !availableBooks.isEmpty();
    }

    public boolean thereAreAvailableBooksForCheckin() {
        return !checkedOutBooks.isEmpty();
    }

}
