package com.versalles.emrms.models;

import java.io.Serializable;

/**
 *
 * @author JUANM
 */
public abstract class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String password;
    private String address;
    private String phoneNumber;

    public User(String id, String name, String password, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public abstract void displayMenu();
}
