package com.sparta.imenu_client.model;

/**
 * Created by Hussein Abu Maash on 3/18/2016.
 */

public class Review {
    private int id;
    private int userId;
    private String reviewText;
    private int rating;

    public Review(int userId, String reviewText, int rating) {
        this.userId = userId;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
