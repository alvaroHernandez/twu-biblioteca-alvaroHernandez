package com.twu.biblioteca;

import com.twu.biblioteca.controllers.Catalog;
import com.twu.biblioteca.presentation.CustomerView;
import com.twu.biblioteca.presentation.LibrarianView;

public class BibliotecaApp {

    private static final String LIBRARIAN_USER = "librarian";
    private static final String CUSTOMER_USER = "customer";

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

        //create customer or librarian view and enable menu interactivity according to arguments
        if(parsedArguments != null) {
            if (LIBRARIAN_USER.equals(parsedArguments.user)) {
                LibrarianView s = new LibrarianView(new Catalog(), parsedArguments.menuEnabled);
                return;
            } else if (CUSTOMER_USER.equals( parsedArguments.user)) {
                CustomerView s = new CustomerView(new Catalog(),  parsedArguments.menuEnabled);
                return;
            }
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
                if(MENU_ENABLED_ARGUMENT.equals(args[0])) {
                    return new ParsedArguments(args[1], true);
                }
            }else if (args.length == 1){
                return new ParsedArguments(args[1],false);
            }
            return null;
        }
    }

}
