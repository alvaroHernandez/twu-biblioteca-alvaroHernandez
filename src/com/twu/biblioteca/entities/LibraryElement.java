package com.twu.biblioteca.entities;

import java.util.HashMap;

/**
 * Created by alvarohernandez on 6/13/17.
 */
public interface LibraryElement {

    String getAsSimpleListElement();

    HashMap<String,String> getDetails();

    String getCurrentOwner();

    void setCurrentOwner(String currentOwner);
}
