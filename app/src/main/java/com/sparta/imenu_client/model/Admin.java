package com.sparta.imenu_client.model;

import java.util.Date;

/**
 * Created by Hussein Abu Maash on 3/25/2016.
 */

public class Admin {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Date jobStart;
    Admin(){}
    public Admin(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getJobStart() {
        return jobStart;
    }

    public void setJobStart(Date jobStart) {
        this.jobStart = jobStart;
    }
}
