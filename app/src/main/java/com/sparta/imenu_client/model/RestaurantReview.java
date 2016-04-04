package com.sparta.imenu_client.model;

/**
 * Created by Hussein Abu Maash on 3/18/2016.
 */

public class RestaurantReview extends Review {
    private Restaurant restaurant;

    public RestaurantReview(Restaurant restaurant,User user, String reviewText, int rating) {
        super(user, reviewText, rating);
        this.restaurant=restaurant;
    }
}
