package com.twu.biblioteca;

import javafx.scene.LightBase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by alvarohernandez on 5/25/17.
 */
public class Menu {

    private static final String INVALID_OPTION_MESSAGE = "Select a valid option!\n";;
    LinkedList<String> options = new LinkedList<String>();
    private String name;
    private boolean opened;

    public Menu(String name) {
        this.name = name;
        this.opened = true;
    }

    public String getMenuString(){
        StringBuilder menuString = new StringBuilder();

        menuString.append(this.name);
        menuString.append(":\n");

        for (int i = 0; i < options.size(); i++) {
            menuString.append(options.get(i));
            menuString.append("\n");
        }

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

    public String executeOption(String option){
        String result = option + " Selected\n";
        if (option.equals("Book Details")) {
            Library library = new Library();
            library.start();
            result += library.getDetailedBookDataAsColumns();
        }else if (option.equals("Book List")) {
            Library library = new Library();
            library.start();
            result += library.getAvailableBooksAsRows();
        }else if(option.equals("Quit")){
            opened = false;
            result += "Bye!";
        }
        return result;
    }

    public String selectOption(String i) throws IllegalAccessException{
        if(!opened)
            throw new IllegalAccessException();
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
