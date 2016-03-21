package com.sparta.imenu_client.service;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;
import android.widget.Toast;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.LoginActivity;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hussein Abu Maash on 3/10/2016.
 */

public class LoginAuthService extends AsyncTask<Void, Void, Boolean> {
    LoginActivity context;

    public LoginAuthService(LoginActivity context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final String url = context.getString(R.string.url)+"user/authenticate";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Pair<String,String> credentials= new Pair<>(context.getUsername(),context.getPassword());
        Log.i("LoginActivity", credentials.toString());
        boolean result = restTemplate.postForObject(url, credentials, boolean.class);
        Log.i("LoginActivity","button pressed");
        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result)
            Toast.makeText(context, "Log in successful", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "Incorrect email or password", Toast.LENGTH_LONG).show();
    }
}