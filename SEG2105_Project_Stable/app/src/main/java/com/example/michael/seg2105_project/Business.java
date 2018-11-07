package com.example.jack.seg_project;

import java.util.ArrayList;

public class Business implements User{
    
    private final char permission = 'b';
    private String username;
    private String password;
    private String email;
    private String fname;
    private String lname;

    private ArrayList<Service> linked = new ArrayList<Service>();
    
    public Business(String username, String password, String email, String fname, String lname){
        username = this.username;
        password = this.password;
        email = this.email;
        fname = this.fname;
        lname = this.lname;
        ArrayList<Service> linked = new ArrayList<Service>();
    }

    public void setUsername(String username){
        username = this.username;
    }

    public void setPassword(String password){
        password = this.password;
    }
    
    public void setEmail(String email){
        email = this.email;
    }
    
    public void setName(String fname, String lname){
        fname = this.fname;
        lname = this.lname;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public char getPermission(){
        return permission;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getFname(){
        return fname;
    }
    
    public String getLname(){
        return lname;
    }

    public void addService(Service service){
        this.linked.add(service);
    }

}
