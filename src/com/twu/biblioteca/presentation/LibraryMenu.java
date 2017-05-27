package com.twu.biblioteca.presentation;

import com.twu.biblioteca.controller.Catalog;

import java.util.LinkedList;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class LibraryMenu {

    private static final String INVALID_OPTION_MESSAGE = "Select a valid option!\n";;
    private static final String QUIT_MESSAGE = "Bye!";
    public static final String BOOK_CHECKOUT_PROMPT_MESSAGE = "Please, type the id for the book you would like to check out:";
    public static final String BOOK_CHECKIN_PROMPT_MESSAGE = "Please, type the id for the book you would like to check in:";

    public static final String BOOKS_LIST_OPTION = "Books List";
    public static final String BOOKS_DETAILS_OPTION = "Books Details";

    public static final String BOOK_CHECKIN_OPTION = "Book Check-In";
    public static final String BOOK_CHECKOUT_OPTION = "Book Check-Out";

    public static final String QUIT_OPTION = "Quit";
    public static final String LISTING_AVAILABLE_BOOKS_MESSAGE = "Listing Available Books:\n\n";

    private Catalog catalog;
    private String name;
    private boolean opened;
    LinkedList<String> options = new LinkedList<String>();
    private String waitingForArgument;

    public LibraryMenu(String name, Catalog catalog) {
        this.name = name;
        this.catalog = catalog;
        this.opened = true;
    }

    public String getMenuString(){
        StringBuilder menuString = new StringBuilder();
        menuString.append("\n-------------------------------------\n");
        menuString.append(this.name);
        menuString.append(":");
        menuString.append("\n-------------------------------------\n");

        for (int i = 0; i < options.size(); i++) {
            menuString.append( getOptionString(i));
            menuString.append("\n");
        }
        menuString.append("-------------------------------------\n");
        menuString.append("Option:");

        return menuString.toString();
    }

    public String getName() {
        return name;
    }

    public void addOption(String option) {
        options.add(option);
    }

    public String getOptionString(int index) {
        return index+1 + ". " + options.get(index);
    }

    private String executeOption(String option){
        String result = option + " Selected\n";
        if (option.equals(BOOKS_LIST_OPTION)) {
            result += catalog.getAvailableBooksListString();
        }else if (option.equals(BOOKS_DETAILS_OPTION)) {
            result += catalog.getDetailedBookDataAsColumnsString();
        }else if(option.equals(BOOK_CHECKOUT_OPTION)){
            if(catalog.thereAreAvailableBooksForCheckout()) {
                result += LISTING_AVAILABLE_BOOKS_MESSAGE;
                result += catalog.getAvailableBooksListString();
                result += BOOK_CHECKOUT_PROMPT_MESSAGE;
                waitForOptionArgument(BOOK_CHECKOUT_OPTION);
            }else{
                result += "No Books Available for Check-Out";
            }
        }else if(option.equals(BOOK_CHECKIN_OPTION)){
            if(catalog.thereAreAvailableBooksForCheckin()) {
                result += "Listing Borrowed Books\n";
                result += catalog.getCheckedOutBooksAsRows();
                result += BOOK_CHECKIN_PROMPT_MESSAGE;
                waitForOptionArgument(BOOK_CHECKIN_OPTION);
            }else{
                result += "No Books Available for Check-In";
            }
        }else if(option.equals(QUIT_OPTION)){
            opened = false;
            result += QUIT_MESSAGE;
        }
        return result;
    }

    private void waitForOptionArgument(String option) {
        waitingForArgument = option;
    }

    public boolean isWaitingForOptionArgument(){
        return waitingForArgument != null;
    }

    public String sendOptionArgument(String argument){
        String result = null;
        //currently we are only waiting for int
        try{
            int value = Integer.valueOf(argument);
            if(waitingForArgument.equals(BOOK_CHECKOUT_OPTION)){
                result = catalog.checkOutBook(value);
            }
            if(waitingForArgument.equals(BOOK_CHECKIN_OPTION)){
                result = catalog.checkInBook(value);
            }
        }catch (NumberFormatException e){
            result = "Invalid Book Selection";
        }
        waitingForArgument  = null;
        return result;
    }

    public String selectOption(String i) throws IllegalAccessException{
        if(!opened)
            throw new IllegalAccessException("LibraryMenu is Closed");
        try{
            int value = Integer.valueOf(i)-1;
            if(value >= 0 && value < options.size()){
                return executeOption(options.get(value));
            }
        }catch (NumberFormatException ignored){ }
        return INVALID_OPTION_MESSAGE;

    }


    public boolean isClosed() {
        return !opened;
    }
}
