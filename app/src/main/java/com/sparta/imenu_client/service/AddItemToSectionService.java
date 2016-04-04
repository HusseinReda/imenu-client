package com.sparta.imenu_client.service;

import android.os.AsyncTask;
import android.util.Pair;

import com.sparta.imenu_client.model.Item;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by hamed on 3/19/16.
 */
public class AddItemToSectionService extends AsyncTask<Void,Void,String> {
    private String url;
    private int sectionId;
    private Item item;

    public AddItemToSectionService(String url, int sectionId, Item item) {
        this.url = url;
        this.sectionId = sectionId;
        this.item = item;
    }

    @Override
    protected String doInBackground(Void... params) {
        Pair<Integer,Item> itemData = new Pair<>(sectionId, item);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate. getMessageConverters().add(new StringHttpMessageConverter());
        String result= restTemplate.postForObject(url,itemData,String.class);
        return result;
    }
}
