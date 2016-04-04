package com.sparta.imenu_client.service;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.sparta.imenu_client.model.Contact;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by hamed on 3/19/16.
 */
public class AddContactService extends AsyncTask<Void,Void,String> {
    private int restaurantId ;
    private Contact contact;
    private String url;
    AddContactService (int restaurantId , Contact contact){
        this.restaurantId =restaurantId;
        this.contact = contact;
        this.url = url;
    }
    @Override
    protected String doInBackground(Void... params) {
        Pair<Integer,Contact> contactData =  new Pair<>(restaurantId,contact);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String result = restTemplate.postForObject(url,contactData,String.class);
        return result;
    }
}
