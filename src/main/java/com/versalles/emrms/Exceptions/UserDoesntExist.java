package com.versalles.emrms.Exceptions;

public class UserDoesntExist extends Exception{
    public UserDoesntExist(String type){
        super("The " + type + " doesn't exist");
    }
}
