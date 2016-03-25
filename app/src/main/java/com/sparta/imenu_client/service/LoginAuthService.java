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
import com.sparta.imenu_client.model.UserRequest;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hussein Abu Maash on 3/10/2016.
 */

public class LoginAuthService extends AsyncTask<Void, Void, Boolean> {
    LoginActivity context;
    UserRequest userRequest;

    public LoginAuthService(LoginActivity context, UserRequest userRequest) {
        this.context = context;
        this.userRequest=userRequest;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final String url = context.getString(R.string.url)+"user/authenticate";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        Log.i("LoginActivity", userRequest.toString());
        boolean result = restTemplate.postForObject(url, userRequest, boolean.class);
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