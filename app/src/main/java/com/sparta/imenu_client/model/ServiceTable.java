package com.sparta.imenu_client.model;

/**
 * Created by Hussein Abu Maash on 5/20/2016.
 */

public class ServiceTable {
    private long id;
    private String restaurantName;
    private int secretNumber;
    private int state;
    /*
    1 -> unlocked
    2 -> locked
    3 -> connected
    4 -> unconnected

     */

    public ServiceTable(String restaurantName, int secretNumber, int state) {
        this.restaurantName = restaurantName;
        this.secretNumber = secretNumber;
        this.state = state;
    }

    public ServiceTable(String restaurantName, int state) {
        this.restaurantName = restaurantName;
        this.state = state;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSecretNumber() {
        return secretNumber;
    }

    public void setSecretNumber(int secretNumber) {
        this.secretNumber = secretNumber;
    }
}
