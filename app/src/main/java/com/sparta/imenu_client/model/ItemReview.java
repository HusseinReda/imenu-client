package com.sparta.imenu_client.model;

/**
 * Created by Hussein Abu Maash on 3/18/2016.
 */

public class ItemReview extends Review {
    private int itemId;

    public ItemReview(int itemId, int userId, String reviewText, int rating) {
        super(userId, reviewText, rating);
        this.itemId=itemId;
    }
}
