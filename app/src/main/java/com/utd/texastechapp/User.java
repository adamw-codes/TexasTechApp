package com.utd.texastechapp;

public class User {
    private String name;
    private boolean isAdmin;

    public User(String name, String adminStatus){
        this.name = name;
        if(adminStatus.equals("Yes")){
            isAdmin = true;
        }
        else if(adminStatus.equals("No")){
            isAdmin = false;
        }
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
