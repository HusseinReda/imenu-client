package com.sparta.imenu_client.service;

import android.os.AsyncTask;
import android.util.Pair;

import com.sparta.imenu_client.model.ItemReview;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by hamed on 3/19/16.
 */
public class AddItemReviewService extends AsyncTask<Void,Void,String> {
    private int itemId;
    private ItemReview review;
    private String url;
    public AddItemReviewService(int itemId, ItemReview review) {
        this.itemId = itemId;
        this.review = review;
        this.url = url;
    }

    @Override
    protected String doInBackground(Void... params) {
        Pair<Integer,ItemReview> reviewData = new Pair<>(itemId,review);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String result= restTemplate.postForObject(url,reviewData,String.class);
        return result;
    }
}
