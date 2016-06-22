package com.sparta.imenu_client.service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.HomeActivity;
import com.sparta.imenu_client.model.ConnectionRequest;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Hussein Abu Maash on 6/16/2016.
 */

public class DisconnectToRestaurantService extends AsyncTask<Void,Void,Boolean> {

    Context context;
    ConnectionRequest connectionRequest;

    public DisconnectToRestaurantService(Context context, ConnectionRequest connectionRequest) {
        this.context = context;
        this.connectionRequest = connectionRequest;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final String url = context.getString(R.string.url)+"restaurantConnection/disconnect";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        Boolean response=false;
        try{
            response = restTemplate.postForObject(url,connectionRequest,Boolean.class);
        }
        catch (Exception e){
            Log.i("Connection Service",e.getMessage().toString());
        }
        return response;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        if(response) {
            Auxiliary.connectedToRest = null;
            Auxiliary.serviceTableSecretNumber=-1;
            Toast.makeText(context, "You have been disconnected successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(intent);
        }
        else
            Toast.makeText(context, "There's a problem in the server\nPlease try again later", Toast.LENGTH_SHORT).show();
    }
}
