package com.twu.biblioteca;

public class BibliotecaApp {

    public static void main(String[] args) {
        try {
            Screen s = new Screen();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
