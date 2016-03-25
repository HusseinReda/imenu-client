package com.sparta.imenu_client.model;

/**
 * Created by hamed on 3/19/16.
 */
public class Contact {
    public Contact(String address, String phoneNumber, int id) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }
    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getId() {
        return id;
    }

    private String address;
    private String phoneNumber;
    private int id;

}
