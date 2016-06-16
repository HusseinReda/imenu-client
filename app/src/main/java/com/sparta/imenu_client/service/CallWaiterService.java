package com.sparta.imenu_client.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sparta.imenu_client.R;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Hussein Abu Maash on 6/16/2016.
 */

public class CallWaiterService extends AsyncTask<Void,Void,Boolean> {

    Context context;
    int secretNumber;

    public CallWaiterService(Context context, int secretNumber) {
        this.context = context;
        this.secretNumber = secretNumber;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final String url = context.getString(R.string.url)+"waiter/call";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        Boolean response=false;
        try{
            response=restTemplate.postForObject(url,secretNumber,Boolean.class);
        }catch (Exception e){
            Log.i("Call waiter",e.getMessage().toString());
        }
        return null;
    }
}
