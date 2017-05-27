package com.twu.biblioteca.presentation;

import com.twu.biblioteca.controllers.Catalog;

import java.util.Scanner;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public abstract class View {

    private boolean menuEnabled = true;

    private static final String welcomeMessage = "Welcome to Biblioteca!";

    Catalog catalog;

    View(Catalog catalog, boolean menuEnabled){
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

    private String getWelcomeMessage(){
        //library is ready just when a catalog is initialized
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
        //load menu according to subclass
        LibraryMenu libraryMenu = loadMenu();

        //setup interactivity
        Scanner reader = new Scanner(System.in);
        try {
            interact(reader, libraryMenu);
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private void interact(Scanner reader, LibraryMenu libraryMenu) throws IllegalAccessException {
        //keeps asking for input until menu receive quit signal
        while(!libraryMenu.isClosed()){
            System.out.print(libraryMenu.getMenuString());
            String input = reader.nextLine();
            String result = libraryMenu.selectOption(input);
            System.out.print(result);

            //check if menu need argument for the las option selected in order to continue
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
