package com.twu.biblioteca.entities;

/**
 * Created by alvarohernandez on 6/14/17.
 */
public class User {

    private String name;
    private String password;
    private String email;
    private String address;
    private String phone;


    public User(String name, String password, String email, String address, String phone) {
        this.name = name;
        this.password= password;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public String getName(){
        return name;
    }

    public boolean passwordIs(String password) {
        return this.password.equals(password);
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
