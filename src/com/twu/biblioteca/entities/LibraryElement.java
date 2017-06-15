package com.twu.biblioteca.entities;

import java.util.HashMap;

/**
 * Created by alvarohernandez on 6/13/17.
 */
public abstract class LibraryElement {

    protected int id;
    protected String currentOwner;
    public abstract HashMap<String,String> getDetails();

    public abstract String getCurrentOwner();

    abstract void setCurrentOwner(String currentOwner);

    abstract String getTextIdentifier();

    public String getAsSimpleListElement() {
        StringBuilder elementString = new StringBuilder();
        elementString.append(id).append(". ").append(getTextIdentifier());
        if(currentOwner != null){
            elementString.append(" - CheckedOutBy: ").append(currentOwner);
        }
        elementString.append("\n");
        return  elementString.toString();
    }


}
