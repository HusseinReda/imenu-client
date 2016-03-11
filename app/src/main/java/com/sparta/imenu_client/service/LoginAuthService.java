package com.sparta.imenu_client.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.sparta.imenu_client.R;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Hussein Abu Maash on 3/10/2016.
 */

public class LoginAuthService extends AsyncTask<Void, Void, String> {
    Context context;

    public LoginAuthService(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        final String url = context.getString(R.string.url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String result = restTemplate.getForObject(url, String.class);
        Log.i("LoginActivity","button pressed");
        return result;
  //      return "";
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result+"\nLog in successful", Toast.LENGTH_LONG).show();
    }
}