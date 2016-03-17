package com.sparta.imenu_client.service;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.LoginActivity;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hussein Abu Maash on 3/10/2016.
 */

public class LoginAuthService extends AsyncTask<Void, Void, String> {
    LoginActivity context;

    public LoginAuthService(LoginActivity context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        final String url = context.getString(R.string.url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        List<String> credentials = new ArrayList<>();
        credentials.add(context.getUsername());
        credentials.add(context.getPassword());
        Log.i("LoginActivity",credentials.toString());
        String result = restTemplate.getForObject(url, String.class,credentials);
        Log.i("LoginActivity","button pressed");
        return result;
  //      return "";
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result+"\nLog in successful", Toast.LENGTH_LONG).show();
    }
}