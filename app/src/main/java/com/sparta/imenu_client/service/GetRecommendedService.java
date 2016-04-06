package com.sparta.imenu_client.service;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.HomeActivity;
import com.sparta.imenu_client.activity.LoginActivity;
import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.model.Restaurant;
import com.sparta.imenu_client.model.UserRequest;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Hussein Abu Maash on 4/6/2016.
 */

public class GetRecommendedService extends AsyncTask<Void, Void, Item[]> {
    HomeActivity context;
    String username;
    Exception error;

    public GetRecommendedService() {
    }

    public GetRecommendedService(HomeActivity context, String username) {

        this.context = context;
        this.username = username;
    }

    @Override
    protected Item[] doInBackground(Void... params) {
        Log.i("recommended Act", "service started");
        final String url = context.getString(R.string.url)+"recommended";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        try {
            Item meals[] = restTemplate.getForObject(url, Item[].class, username);
            return meals;
        }
        catch (Exception e){
            error=e;
            return null;
        }
    }
    @Override
    protected void onPostExecute(Item[] restaurants) {
        if(error!=null){
            //build list view
        }
        else {
            //show msg showing bad service
        }
    }
}
