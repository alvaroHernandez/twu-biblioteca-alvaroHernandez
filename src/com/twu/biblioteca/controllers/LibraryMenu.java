package com.twu.biblioteca.controllers;

import com.twu.biblioteca.BibliotecaApp;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by alvarohernandez on 6/13/17.
 */
public class LibraryMenu {

    private static final String INVALID_OPTION_MESSAGE = "Select a valid option!\n";;
    private static final String QUIT_MESSAGE = "Bye!";
    public static final String BOOK_CHECKOUT_PROMPT_MESSAGE = "Please, type the id for the book you would like to check out:";
    public static final String BOOK_CHECKIN_PROMPT_MESSAGE = "Please, type the id for the book you would like to check in:";

    public static final String MOVIE_CHECKOUT_PROMPT_MESSAGE = "Please, type the id for the movie you would like to check out:";
    public static final String MOVIE_CHECKIN_PROMPT_MESSAGE = "Please, type the id for the movie you would like to check in:";

    public static final String BOOKS_LIST_OPTION = "Books List";
    public static final String MOVIES_LIST_OPTION = "Movies List";

    public static final String BOOKS_DETAILS_OPTION = "Books Details";
    public static final String MOVIES_DETAILS_OPTION = "Movies Details";

    public static final String BOOK_CHECKIN_OPTION = "Book Check-In";
    public static final String MOVIE_CHECKIN_OPTION = "Movie Check-In";

    public static final String BOOK_CHECKOUT_OPTION = "Book Check-Out";
    public static final String MOVIE_CHECKOUT_OPTION = "Movie Check-Out";

    public static final String LISTING_AVAILABLE_BOOKS_MESSAGE = "Listing Available Books:";
    public static final String LISTING_AVAILABLE_MOVIES_MESSAGE = "Listing Available Movies:";

    public static final String QUIT_OPTION = "Quit";
    public static final String SHOW_USER_DATA = "Show User Data";

    private InputStream systemIn;

    private String name;
    private LinkedList<String> options = new LinkedList<String>();
    private String waitingForArgument;
    private boolean opened;
    protected LibraryController libraryController;
    public LibraryMenu(String name, LibraryController libraryController) {
        this(System.in,name,libraryController);
    }

    public LibraryMenu(InputStream in, String name, LibraryController libraryController) {
        systemIn = in;
        this.name = name;
        this.opened = true;
        this.libraryController = libraryController;
        loadMenu();
    }


    public static LibraryMenu createMenu(String userType, LibraryController controller){
        if (BibliotecaApp.LIBRARIAN_USER.equals(userType)) {
            return new LibrarianMenu(controller);
        } else if (BibliotecaApp.CUSTOMER_USER.equals( userType)) {
            return new CustomerMenu(controller);
        }
        return  null;
    }

    LibraryMenu loadMenu() {
        return this;
    }

    public void setupInteraction(LibraryMenu menu){
        //setup interactivity
        Scanner reader = new Scanner(systemIn);
        try {
            interact(reader, menu);
        } catch (IllegalAccessException e) {
            libraryController.sendMessage(e.getMessage());
        }
    }

    public void interact(Scanner reader, LibraryMenu libraryMenu) throws IllegalAccessException {
        //keeps asking for input until menu receive quit signal
        while(!libraryMenu.isClosed()){
            libraryController.sendMessage(libraryMenu.getMenuString());
            String input = reader.nextLine();
            libraryMenu.selectOption(input);

            //check if menu need argument for the las option selected in order to continue
            if(libraryMenu.isWaitingForOptionArgument()){
                input = reader.nextLine();
                libraryMenu.sendOptionArgument(input);
            }
        }
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
        //compose result string foreach possible option
        String optionSelectedMessage = option + " Selected\n";
        libraryController.sendMessage(optionSelectedMessage);
        if(option.equals(SHOW_USER_DATA)){
            libraryController.getUserData();
        }else if (option.equals(BOOKS_LIST_OPTION)) {
            libraryController.getAvailableBooksListString();
        }else if(option.equals(MOVIES_LIST_OPTION)) {
            libraryController.getAvailableMoviesListString();
        }else if (option.equals(BOOKS_DETAILS_OPTION)) {
            libraryController.getDetailedBookDataAsColumnsString();
        }else if (option.equals(MOVIES_DETAILS_OPTION)) {
            libraryController.getDetailedMovieDataAsColumnsString();
        }else if(option.equals(BOOK_CHECKOUT_OPTION)){
            if(libraryController.thereAreAvailableBooksForCheckOut()) {
                libraryController.sendMessage(LISTING_AVAILABLE_BOOKS_MESSAGE);
                libraryController.getAvailableBooksListString();
                libraryController.sendMessage(BOOK_CHECKOUT_PROMPT_MESSAGE);
                waitForOptionArgument(BOOK_CHECKOUT_OPTION);
            }else{
                libraryController.sendMessage("No Books Available for Check-Out");
            }
        }else if(option.equals(MOVIE_CHECKOUT_OPTION)){
            if(libraryController.thereAreAvailableMoviesForCheckOut()) {
                libraryController.sendMessage(LISTING_AVAILABLE_MOVIES_MESSAGE);
                libraryController.getAvailableMoviesListString();
                libraryController.sendMessage(MOVIE_CHECKOUT_PROMPT_MESSAGE);
                waitForOptionArgument(MOVIE_CHECKOUT_OPTION);
            }else{
                libraryController.sendMessage("No Movies Available for Check-Out");
            }
        }else if(option.equals(BOOK_CHECKIN_OPTION)){
            if(libraryController.thereAreAvailableBooksForCheckIn()) {
                libraryController.sendMessage("Listing Borrowed Books\n");
                libraryController.getCheckedOutBooksAsRows();
                libraryController.sendMessage(BOOK_CHECKIN_PROMPT_MESSAGE);
                waitForOptionArgument(BOOK_CHECKIN_OPTION);
            }else{
                libraryController.sendMessage("No Books Available for Check-In");
            }
        }else if(option.equals(MOVIE_CHECKIN_OPTION)){
            if(libraryController.thereAreAvailableMoviesForCheckIn()) {
                libraryController.sendMessage("Listing Borrowed Movies\n");
                libraryController.getCheckedOutMoviesAsRows();
                libraryController.sendMessage(MOVIE_CHECKIN_PROMPT_MESSAGE);
                waitForOptionArgument(MOVIE_CHECKIN_OPTION);
            }else{
                libraryController.sendMessage("No Movies Available for Check-In");
            }
        }else if(option.equals(QUIT_OPTION)) {
            opened = false;
            libraryController.sendMessage(QUIT_MESSAGE);
        }
        return optionSelectedMessage;
    }

    private void waitForOptionArgument(String option) {
        waitingForArgument = option;
    }

    public boolean isWaitingForOptionArgument(){
        return waitingForArgument != null;
    }

    public void sendOptionArgument(String argument){

        try{
            //parse argument (currently we are only waiting for int) and send to last selected option in the menu
            int value = Integer.valueOf(argument);
            if(waitingForArgument.equals(BOOK_CHECKOUT_OPTION)){
                libraryController.checkOutBook(value);
            }else if(waitingForArgument.equals(BOOK_CHECKIN_OPTION)){
                libraryController.checkInBook(value);
            }else if(waitingForArgument.equals(MOVIE_CHECKOUT_OPTION)){
                libraryController.checkOutMovie(value);
            }else if(waitingForArgument.equals(MOVIE_CHECKIN_OPTION)){
                libraryController.checkInMovie(value);
            }

        }catch (NumberFormatException e ){
            libraryController.sendMessage("Invalid Book Selection");
        }catch (IllegalAccessException e){
            libraryController.sendMessage(e.getMessage());
        }
        //clean last selection option
        waitingForArgument  = null;
    }

    public String selectOption(String i) throws IllegalAccessException{
        if(!opened)
            throw new IllegalAccessException("LibraryController is Closed");
        try{
            //using offset (1) to map from input selection to array index
            int value = Integer.valueOf(i)-1;
            if(value >= 0 && value < options.size()){
                return executeOption(options.get(value));
            }
        }catch (NumberFormatException ignored){}
        return INVALID_OPTION_MESSAGE;
    }




    public boolean isClosed() {
        return !opened;
    }


}
