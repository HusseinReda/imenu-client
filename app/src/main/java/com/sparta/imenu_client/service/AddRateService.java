package com.sparta.imenu_client.service;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.sparta.imenu_client.R;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Hussein Abu Maash on 4/27/2016.
 */

public class AddRateService extends AsyncTask<Void, Void, Boolean> {
    private Context callingActivity;
    private int id;
    private boolean itemFlag; // 1 for item , 0 for restaurant
    private String requiredToRate;
    private float rating;
    private Exception error;

    public AddRateService(Context callingActivity, int id, float rating, boolean itemFlag) {
        this.callingActivity = callingActivity;
        this.id = id;
        this.itemFlag = itemFlag;
        if (itemFlag)
            requiredToRate="item/";
        else
            requiredToRate="restaurant/";
        this.rating = rating;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        Log.i("addRate service", "service started");
        String url = callingActivity.getString(R.string.url)+requiredToRate+"addRating";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        try {
            Boolean result = restTemplate.postForObject(url, rating, boolean.class);
            return result;
        }
        catch (Exception e){
            error=e;
            Log.i("getItem service","error caught");
            return null;
        }
    }

    @Override
    protected void onPostExecute(Boolean result){
        if(error!=null && result)
            Toast.makeText(callingActivity,"Your rating is added successfully",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(callingActivity,"There is a problem in the server\nPlease try again later",Toast.LENGTH_SHORT).show();
    }
}
