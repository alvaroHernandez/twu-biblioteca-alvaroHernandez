package com.twu.biblioteca;

import com.twu.biblioteca.controllers.LibraryController;
import com.twu.biblioteca.controllers.LibrarianMenu;
import com.twu.biblioteca.controllers.LibraryMenu;
import com.twu.biblioteca.presentation.View;
import com.twu.biblioteca.services.Catalog;

public class BibliotecaApp {

    public static final String LIBRARIAN_USER = "librarian";
    public static final String CUSTOMER_USER = "customer";

    private static final String MENU_ENABLED_ARGUMENT = "-m";

    private static final String USAGE_MESSAGE =
            "Usage:\n"+
            "BibliotecaApp [-m] librarian\n"+
            "BibliotecaApp [-m] customer\n"+
            "\n"+
            "Options:\n"+
            "-m     Enable menu interaction";

    public static void main(String[] args) {
        ParsedArguments parsedArguments = ParsedArguments.parseArguments(args);

        if(parsedArguments != null) {
            View view = new View();
            LibraryController controller = new LibraryController(new Catalog(),view);
            if(parsedArguments.menuEnabled){
                LibraryMenu menu = LibrarianMenu.createMenu(parsedArguments.user,controller);
                if(menu!= null)
                    menu.setupInteraction(menu);
            }else{
                controller.getAvailableBooks();
            }
            return;
        }
        printUsage();
    }

    private static void printUsage() {
        System.out.println(USAGE_MESSAGE);
    }


    private static class ParsedArguments{
        private boolean menuEnabled;
        private String user;

        private ParsedArguments(String user,boolean menuEnabled){
            this.user = user;
            this.menuEnabled = menuEnabled;
        }

        private static ParsedArguments parseArguments(String[] args){
            if(args.length == 2){
                if(MENU_ENABLED_ARGUMENT.equals(args[0]) && validateUser(args[1])) {
                    return new ParsedArguments(args[1], true);
                }
            }else if (args.length == 1 && validateUser(args[0])){
                return new ParsedArguments(args[0],false);
            }
            return null;
        }

        private static boolean validateUser(String user) {
            if (user.equals(LIBRARIAN_USER) || user.equals(CUSTOMER_USER)) {
                return true;
            }
            return false;
        }
    }

}
