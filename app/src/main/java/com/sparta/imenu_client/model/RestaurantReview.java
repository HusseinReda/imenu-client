package com.sparta.imenu_client.model;

/**
 * Created by Hussein Abu Maash on 3/18/2016.
 */

public class RestaurantReview extends Review {
    private int restaurantId;

    public RestaurantReview(int restaurantId, int userId, String reviewText, int rating) {
        super(userId, reviewText, rating);
        this.restaurantId=restaurantId;
    }
}
