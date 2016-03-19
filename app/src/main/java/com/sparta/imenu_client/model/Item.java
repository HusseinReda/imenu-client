package com.sparta.imenu_client.model;

import java.util.List;

/**
 * Created by hamed on 3/19/16.
 */
public class Item {
    private int id;
    private int restaurantId;
    private int sectionId;
    private String name;
    private String type;
    private double price;
    private String description;
    private List<String> keywords;
    private List<ItemReview> reviews;
    private String picture;
    private double rating;

    public Item(int restaurantId, int sectionId, String name, String type, double price, String description, List<String> keywords, String picture) {
        this.restaurantId = restaurantId;
        this.sectionId = sectionId;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
        this.keywords = keywords;
        this.picture = picture;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public int getSectionId() {
        return sectionId;
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

    public List<String> getKeywords() {
        return keywords;
    }

    public List<ItemReview> getReviews() {
        return reviews;
    }

    public String getPicture() {
        return picture;
    }

    public double getRating() {
        return rating;
    }
    public int getId(){
        return id;
    }
    public void addReview (ItemReview review){
        reviews.add( review);
    }
}
