package com.sparta.imenu_client.model;

/**
 * Created by Hussein Abu Maash on 6/16/2016.
 */

public class ConnectionRequest {
    private long id;
    private User user;
    private int secretNumber;

    public ConnectionRequest() {
    }

    public ConnectionRequest(User user, int secretNumber) {
        this.user = user;
        this.secretNumber = secretNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getSecretNumber() {
        return secretNumber;
    }

    public void setSecretNumber(int secretNumber) {
        this.secretNumber = secretNumber;
    }
}
