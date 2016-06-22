package com.sparta.imenu_client.service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.RestaurantActivity;
import com.sparta.imenu_client.model.Restaurant;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Hussein Abu Maash on 4/28/2016.
 */

public class GetRestaurantByNameService extends AsyncTask<Void,Void,Restaurant>{
    private String restaurantName;
    private Context context;
    Exception error;

    public GetRestaurantByNameService(Context context,String restaurantName) {
        this.restaurantName=restaurantName;
        this.context=context;
    }

    @Override
    protected Restaurant doInBackground(Void... params) {
        Log.i("getRest service", "service started");
        Log.i("getRest service", "rest name= "+restaurantName);
        final String url = context.getString(R.string.url)+"restaurant/name?name="+restaurantName;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        if(restaurantName!=null)
            Log.i("get rest",restaurantName);
        else
            Log.i("get rest","a7a");
       // try {
            Restaurant restaurant = restTemplate.getForObject(url, Restaurant.class);
            Log.i("getRest service","name = "+restaurant.getName());
            return restaurant;
       // }
      //  catch (Exception e){
        //    error=e;
        //    Log.i("getRest service","error caught");
        //    return null;
       // }
    }

    @Override
    protected void onPostExecute(Restaurant restaurant) {
        if(restaurant!=null){
            Intent restaurantIntent = new Intent(context, RestaurantActivity.class);
            restaurantIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            restaurantIntent.putExtra("restaurant", restaurant);
            context.startActivity(restaurantIntent);
        }
        else{
            Toast.makeText(context, "There is a problem in the server\nPlease try again later", Toast.LENGTH_SHORT).show();
        }
    }
}
