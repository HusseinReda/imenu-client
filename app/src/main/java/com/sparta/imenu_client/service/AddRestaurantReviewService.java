package com.sparta.imenu_client.service;

import android.os.AsyncTask;
import android.util.Pair;

import com.sparta.imenu_client.model.RestaurantReview;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by hamed on 3/19/16.
 */
public class AddRestaurantReviewService extends AsyncTask <Void,Void,String> {
    private String url;
    private int resturantId;
    private RestaurantReview review;

    public AddRestaurantReviewService(String url, int resturantId, RestaurantReview review) {
        this.url = url;
        this.resturantId = resturantId;
        this.review = review;
    }
    protected String doInBackground(Void... params){
        Pair <Integer,RestaurantReview> reviewData = new Pair<>(resturantId,review);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add (new StringHttpMessageConverter());
        String result= restTemplate.postForObject(url, reviewData,String.class);
        return result;
    }
}
