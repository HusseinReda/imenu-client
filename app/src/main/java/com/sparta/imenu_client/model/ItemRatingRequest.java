package com.sparta.imenu_client.model;

/**
 * Created by Hussein Abu Maash on 4/29/2016.
 */

public class ItemRatingRequest {
    float x;
    int itemId;

    public ItemRatingRequest(int itemId,float x) {
        this.x = x;
        this.itemId = itemId;
    }
}
