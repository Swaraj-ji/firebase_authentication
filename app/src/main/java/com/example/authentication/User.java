package com.example.authentication;

public class User {
    public String full_name , dob , email;

    public User(){

    }
    public User(String full_name, String dob, String email){
        this.full_name = full_name;
        this.dob = dob;
        this.email = email;
    }
}
