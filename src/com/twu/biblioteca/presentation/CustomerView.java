package com.twu.biblioteca.presentation;

import com.twu.biblioteca.controllers.Catalog;

/**
 * Created by alvarohernandez on 5/27/17.
 */
public class CustomerView extends View {


    public CustomerView(Catalog catalog, boolean menuEnabled) {
        super(catalog,menuEnabled);
    }

    @Override
    LibraryMenu loadMenu() {
        LibraryMenu libraryMenu = new LibraryMenu("Customer LibraryMenu",this.catalog);
        libraryMenu.addOption(LibraryMenu.BOOKS_LIST_OPTION);
        libraryMenu.addOption(LibraryMenu.BOOKS_DETAILS_OPTION);
        libraryMenu.addOption(LibraryMenu.QUIT_OPTION);
        return libraryMenu;
    }


}
