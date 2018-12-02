package com.example.mike9.seg2105_project;

public class UserInformation {
    private String firstname;
    private String lastname;

    public UserInformation(){

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String toString(){
        return getFirstname()+"\n"+getLastname();
    }
}
