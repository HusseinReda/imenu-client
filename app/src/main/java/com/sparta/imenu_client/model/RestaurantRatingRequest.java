package com.sparta.imenu_client.model;

/**
 * Created by Hussein Abu Maash on 4/29/2016.
 */

public class RestaurantRatingRequest {
    float x;
    int restautrantId;

    public RestaurantRatingRequest(int restautrantId, float x) {
        this.restautrantId = restautrantId;
        this.x = x;
    }
}
