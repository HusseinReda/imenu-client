package com.sparta.imenu_client.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.HomeActivity;
import com.sparta.imenu_client.model.ConnectionRequest;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

/**
 * Created by Hussein Abu Maash on 6/16/2016.
 */

public class ConnectToRestaurantService extends AsyncTask<Void,Void,String> {

    Context context;
    ConnectionRequest connectionRequest;

    public ConnectToRestaurantService(Context context, ConnectionRequest connectionRequest) {
        this.context = context;
        this.connectionRequest = connectionRequest;
    }

    @Override
    protected String doInBackground(Void... params) {
        final String url = context.getString(R.string.url)+"restaurantConnection/connect";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String restaurantName=null;
        try{
            restaurantName = restTemplate.postForObject(url,connectionRequest,String.class);
        }
        catch (Exception e){
            Log.i("Connection Service",e.getMessage().toString());
        }
        return restaurantName;
    }

    @Override
    protected void onPostExecute(String restaurantName) {
        if(restaurantName!=null) {
            ((HomeActivity)context).refreshMenu();
            Log.i("connect service",restaurantName);
            Auxiliary.connectedToRest = restaurantName;
            Auxiliary.serviceTableSecretNumber=connectionRequest.getSecretNumber();
            Auxiliary.initOrder();
            Toast.makeText(context, "You have been connected successfully", Toast.LENGTH_SHORT).show();
            GetRestaurantByNameService getRestaurantByNameService = new GetRestaurantByNameService(context,restaurantName);
            GetTableIdBySecretNumber getTableIdBySecretNumber = new GetTableIdBySecretNumber(context);
            getTableIdBySecretNumber.execute();
            getRestaurantByNameService.execute();


        } else {
            Log.i("connect service", "shit");
            Toast.makeText(context, "There's a problem in the server\nPlease try again later", Toast.LENGTH_SHORT).show();
        }
    }
}
