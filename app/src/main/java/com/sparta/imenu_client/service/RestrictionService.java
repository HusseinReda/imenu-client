package com.sparta.imenu_client.service;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.sparta.imenu_client.model.UserSpec;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Hussein Abu Maash on 3/17/2016.
 */

public class RestrictionService extends AsyncTask<Void, Void, String> {
    private int userId;
    private UserSpec restriction;
    private String operation;
    private String result;

    public RestrictionService(int userId, UserSpec restriction, String operation) {
        this.restriction = restriction;
        this.userId = userId;
        this.operation=operation;
    }
    @Override
    protected String doInBackground(Void... params) {
        final String url = "http://lambada.eu-central-1.elasticbeanstalk.com/user/restriction/"+operation;
        RestTemplate restTemplate = new RestTemplate();
        Pair<Integer,UserSpec> sentData= new Pair<>(userId,restriction);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        result = restTemplate.postForObject(url, sentData, String.class);
        Log.i("Restriction service", result);
        return result;
    }

    public String getResult(){
        return result;
    }
}
