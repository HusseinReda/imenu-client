package com.sparta.imenu_client.model;

/**
 * Created by Hussein Abu Maash on 3/18/2016.
 */

public class ItemReview extends Review {
    private Item item;

    public ItemReview(Item item, User user, String reviewText, int rating) {
        super(user, reviewText, rating);
        this.item =item;
    }
}
