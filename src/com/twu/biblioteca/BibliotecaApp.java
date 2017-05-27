package com.twu.biblioteca;

import com.twu.biblioteca.controller.Catalog;
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
        if(!initView(args))
            printUsage();
    }

    private static boolean initView(String[] args){
        boolean menuEnabled = false;
        String user = "";
        if(args.length == 2){
            if(MENU_ENABLED_ARGUMENT.equals(args[0])){
                menuEnabled = true;
                user = args[1];
            }else{
                return false;
            }
        }else if (args.length == 1){
            user = args[0];
        }

        if (LIBRARIAN_USER.equals(user)) {
            LibrarianView s = new LibrarianView(new Catalog(), menuEnabled);
            return true;
        } else if (CUSTOMER_USER.equals(user)) {
            CustomerView s = new CustomerView(new Catalog(), menuEnabled);
            return true;
        }
        return false;

    }

    private static void printUsage() {
        System.out.println(USAGE_MESSAGE);
    }

}
