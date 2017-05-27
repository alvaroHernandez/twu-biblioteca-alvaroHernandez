package com.twu.biblioteca.presentation;

import com.twu.biblioteca.controller.Catalog;

import java.io.Reader;
import java.util.Scanner;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public abstract class View {

    LibraryMenu libraryMenu;
    private boolean menuEnabled = true;

    private static final String welcomeMessage = "Welcome to Biblioteca!";

    protected Catalog catalog;

    public View(Catalog catalog,boolean menuEnabled){
        this.catalog = catalog;
        this.menuEnabled = menuEnabled;

        System.out.println(getWelcomeMessage());
        if(!menuEnabled){
            printAvailableBooks();
        }else{
            setupInteraction();
        }
    }

    abstract LibraryMenu loadMenu();

    public String getWelcomeMessage(){
        if(catalog!=null){
            return welcomeMessage;
        }
        return null;
    }

    private void printAvailableBooks() {
        System.out.println("Listing available books:\n");
        System.out.println(catalog.getAvailableBooksListString());
        System.out.println("There are no more books to show.");
    }

    private void setupInteraction(){
        this.libraryMenu = loadMenu();
        Scanner reader = new Scanner(System.in);
        try {
            interact(reader,libraryMenu);
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private void interact(Scanner reader, LibraryMenu libraryMenu) throws IllegalAccessException {
        while(!libraryMenu.isClosed()){
            System.out.print(libraryMenu.getMenuString());
            String input = reader.nextLine();
            String result = libraryMenu.selectOption(input);
            System.out.print(result);

            if(libraryMenu.isWaitingForOptionArgument()){
                input = reader.nextLine();
                result  = libraryMenu.sendOptionArgument(input);
                System.out.println(result);
            }
        }
    }

    public boolean isMenuEnabled() {
        return menuEnabled;
    }


}
