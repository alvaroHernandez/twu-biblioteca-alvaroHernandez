package com.twu.biblioteca;

import com.twu.biblioteca.Screen.Printer;

public class BibliotecaApp {

    public static void main(String[] args) {
        try {
            Printer s = new Printer();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
