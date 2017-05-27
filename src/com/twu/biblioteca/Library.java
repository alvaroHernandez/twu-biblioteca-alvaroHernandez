package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class Library extends BibliotecaApp {

    private static final String welcomeMessage = "Welcome!";
    private boolean ready;

    private ArrayList<Book> books= new ArrayList<Book>();


    public String start() {
        books.add(new Book("Building Microservices",1,"Sam Newman", "2015"));
        books.add(new Book("TDD by Example",2,"Kent Beck", "2000"));
        books.add(new Book( "Head First Java",3,"Bert Bates", "2003"));
        ready = true;

        return welcomeMessage;

    }

    public ArrayList<Book> getAvailableBooks() throws IllegalAccessException {
        if(!ready)
            throw  new IllegalAccessException("Library has not been initialized");
        return books;
    }


    public String getDetailHeadersAsColumns() {
        String expectedHeaders = String.format("%s\t\t" + "%s\t\t" + "%s",Book.TITLE_FIELD,Book.AUTHOR_FIELD,Book.YEAR_PUBLISHED_FIELD);
        return expectedHeaders;
    }


    public LinkedHashMap<String,Integer> calculateMaxLengthForEachField(){
        LinkedHashMap<String,Integer> maxLengths = Book.getFieldsHeadersLength();
        for (Book book : books) {
            HashMap<String,String> details = book.getDetails();
            for (String field : maxLengths.keySet()) {
                if ( details.get(field).length() > maxLengths.get(field)){
                    maxLengths.put(field,details.get(field).length());
                }
            }
        }

        return maxLengths;
    }

    public String getDetailedBookDataAsColumns() {
        LinkedHashMap<String,Integer> maxLengths = calculateMaxLengthForEachField();
        StringBuilder dataAsColumns = new StringBuilder();

        appendHeaders(dataAsColumns,maxLengths);
        appendDashes(dataAsColumns);
        appendBookData(dataAsColumns,maxLengths);

        return dataAsColumns.toString();
    }

    private void appendBookData(StringBuilder dataAsColumns, LinkedHashMap<String, Integer> maxLengths) {
        for (Book book : books) {
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
                dataAsColumns.append(field);
                fieldLength = field.length();
            }else{
                dataAsColumns.append(details.get(field));
                fieldLength = details.get(field).length();
            }

            while(fieldLength < (maxLengths.get(field)+4) ){
                dataAsColumns.append(" ");
                fieldLength++;
            }

        }
        dataAsColumns.append("\n");
    }

    public String getAvailableBooksAsRows() {
        StringBuilder result = new StringBuilder();
        for (Library.Book availableBook : books) {
            result.append(availableBook.getId() + ". " + availableBook.getTitle());
            result.append("\n");
        }
        return result.toString();
    }

    public String checkOutBook(int id) {
        return "Book '"+ getBookById(1)+ "' was successfully checked-out";
    }

    private Book getBookById(int id) {
        return books.get(id);
    }

    /**
     * Created by alvarohernandez on 5/25/17.
     */
    public static class Book {

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
}
