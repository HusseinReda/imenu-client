package com.sparta.imenu_client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hamed on 3/19/16.
 */
public class Item implements Serializable{
    private long id;
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
    private List<OrderCard> orderCards;

    public Item() {
    }

    public Section getSection() {
        return section;
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

    public long getId(){
        return id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void addReview (ItemReview review){
        reviews.add( review);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public void setReviews(ArrayList<ItemReview> reviews) {
        this.reviews = reviews;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<OrderCard> getOrderCards() {
        return orderCards;
    }

    public void setOrderCards(List<OrderCard> orderCards) {
        this.orderCards = orderCards;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", rating='"+rating+'\''+
                '}';
    }
}
