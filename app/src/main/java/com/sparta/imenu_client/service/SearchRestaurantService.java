package com.sparta.imenu_client.service;

import android.os.AsyncTask;
import android.util.Log;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.HomeActivity;
import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.model.Restaurant;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Hussein Abu Maash on 4/6/2016.
 */

public class SearchRestaurantService extends AsyncTask<Void, Void, Restaurant[]> {
    HomeActivity context;
    String query;
    Exception error;

    public SearchRestaurantService() {
    }

    public SearchRestaurantService(HomeActivity context, String query) {

        this.context = context;
        this.query = query;
    }

    @Override
    protected Restaurant[] doInBackground(Void... params) {
        Log.i("search meals Act", "service started");
        final String url = context.getString(R.string.url)+"search/restaurant";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        try {
            Restaurant restaurants[] = restTemplate.getForObject(url, Restaurant[].class, query);
            Log.i("search meals act","button pressed");
            return restaurants;
        }
        catch (Exception e){
            error=e;
            return null;
        }
    }
    @Override
    protected void onPostExecute(Restaurant[] restaurants) {
        if(error!=null){
            //build list view
        }
        else {
            //show msg showing bad service
        }
    }

}
