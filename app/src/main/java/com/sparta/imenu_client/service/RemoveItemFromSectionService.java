package com.sparta.imenu_client.service;

import android.os.AsyncTask;
import android.util.Pair;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by hamed on 3/19/16.
 */
public class RemoveItemFromSectionService extends AsyncTask<Void, Void, String> {
    private String url;
    private int sectionId;
    private int itemId;

    public RemoveItemFromSectionService(String url, int sectionId, int itemId) {
        this.url = url;
        this.sectionId = sectionId;
        this.itemId = itemId;
    }

    @Override
    protected String doInBackground(Void... params) {
        Pair<Integer,Integer> itemData=  new Pair<>(sectionId , itemId);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String result=restTemplate.postForObject(url,itemData,String.class);
        return result;
    }
}
