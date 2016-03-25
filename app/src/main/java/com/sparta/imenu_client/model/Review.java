package com.sparta.imenu_client.model;

/**
 * Created by Hussein Abu Maash on 3/18/2016.
 */

public class Review {
    private int id;
    private User user;
    private String reviewText;
    private int rating;

    public Review(User user, String reviewText, int rating) {
        this.user = user;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public User getUserId() {
        return user;
    }

    public void setUserId(User user) {
        this.user = user;
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
