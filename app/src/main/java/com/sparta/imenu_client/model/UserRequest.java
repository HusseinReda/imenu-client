package com.sparta.imenu_client.model;

/**
 * Created by Hussein Abu Maash on 3/25/2016.
 */

public class UserRequest {
    private String name;
    private String email;
    private String password;
    public UserRequest(){}
    public UserRequest(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

