package com.sparta.imenu_client.model;

/**
 * Created by hamed on 3/19/16.
 */
public class Contact {
    public Contact(String address, String phoneNumber, int restaurantId) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.restaurantId = restaurantId;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    private String address;
    private String phoneNumber;
    private int restaurantId;

}
