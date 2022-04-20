package com.utd.texastechapp;

public class User {
    private String name;
    private boolean isAdmin;
    private String userID;

    public User(String name, String adminStatus, String userID){
        this.name = name;
        this.userID = userID;
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

    public String getUserID() {
        return userID;
    }

    public void setAdmin(Boolean adminStatus){
        this.isAdmin = adminStatus;
    }
}
