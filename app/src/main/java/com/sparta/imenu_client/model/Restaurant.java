package com.sparta.imenu_client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hamed on 3/19/16.
 */
public class Restaurant {
    private int id;
    private String name;
    private String category;
    private String description;
    private String picture;
    private Menu menu;
    private List<Contact> contacts;
    private ArrayList<RestaurantReview> reviews;
    private float rating;

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public Menu getMenu() {
        return menu;
    }

    public List getContacts() {
        return contacts;
    }

    public ArrayList<RestaurantReview> getReviews() {
        return reviews;
    }

    public float getRating() {
        return rating;
    }

    public Restaurant(String name, String category, String description, String picture, Menu menu, ArrayList<Contact> contacts) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.picture = picture;
        this.menu = menu;
        this.contacts = contacts;
    }
    public void addContact (Contact contact){
        contacts.add(contact);
        // call the service
    }
    public void addReview (RestaurantReview review){
        reviews.add(review);
        // call the service
    }

}
