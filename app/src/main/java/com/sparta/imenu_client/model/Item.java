package com.sparta.imenu_client.model;

import java.util.ArrayList;

/**
 * Created by hamed on 3/19/16.
 */
public class Item {
    private int id;
    private String name;
    private String restaurantName;
    private String type;
    private double price;
    private String description;
    private ArrayList<String> keywords;
    private ArrayList<ItemReview> reviews;
    private String picture;
    private float rating;
    private Section section;

    public Section getSection() {
        return section;
    }

    public Item() {
    }

    public Item(String name, String restaurantName, String type, double price, String description, ArrayList<String> keywords, String picture) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
        this.keywords = keywords;
        this.picture = picture;
        this.restaurantName=restaurantName;
    }
    
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public ArrayList<ItemReview> getReviews() {
        return reviews;
    }

    public String getPicture() {
        return picture;
    }

    public float getRating() {
        return rating;
    }

    public int getId(){
        return id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void addReview (ItemReview review){
        reviews.add( review);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
