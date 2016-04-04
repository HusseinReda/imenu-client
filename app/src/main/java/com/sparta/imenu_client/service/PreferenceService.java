package com.sparta.imenu_client.service;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.sparta.imenu_client.R;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Hussein Abu Maash on 3/17/2016.
 */

public class PreferenceService extends AsyncTask<Void, Void, String> {
    private int userId;
    private String preference;
    private String operation;
    private String result;

    public PreferenceService(int userId, String preference, String operation){
        this.userId = userId;
        this.preference = preference;
        this.operation=operation;
    }

    @Override
    protected String doInBackground(Void... params) {
        final String url = "http://lambada.eu-central-1.elasticbeanstalk.com/user/preference/"+operation;
        RestTemplate restTemplate = new RestTemplate();
        Pair<Integer,String> sentData= new Pair<>(userId,preference);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        result = restTemplate.postForObject(url, sentData, String.class);
        Log.i("Preference service", result);
        return result;
    }

    public String getResult(){
        return result;
    }
}
