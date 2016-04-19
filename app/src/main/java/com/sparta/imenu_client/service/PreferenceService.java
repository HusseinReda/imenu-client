package com.sparta.imenu_client.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.ProfileActivity;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hussein Abu Maash on 3/17/2016.
 */

public class PreferenceService extends AsyncTask<Void, Void, Boolean> {
    private String email;
    private String preference;
    private String operation;
    private boolean result;
    private ProfileActivity context;

    public PreferenceService(String preference, String operation,ProfileActivity context){
        this.preference = preference;
        this.operation=operation;
        this.context=context;
        SharedPreferences currentUserPref = context.getApplicationContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        email = currentUserPref.getString("email",null);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final String url = context.getString(R.string.url)+"user/"+operation+"Preferences";
        RestTemplate restTemplate = new RestTemplate();
        List<String> sentData = new ArrayList<String>();
        sentData.add(preference);
        sentData.add(email);
        Log.i("preference service", sentData.get(0));
        Log.i("preference service",sentData.get(1));
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        result = restTemplate.postForObject(url, sentData, Boolean.class);
        Log.i("Preference service", String.valueOf(result));
        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result)
            Toast.makeText(context,"Preference "+operation+"ed",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context,"Preference "+operation+"ed",Toast.LENGTH_LONG).show();
    }

    public boolean getResult() {
        return result;
    }
}
