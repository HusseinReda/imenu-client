package com.sparta.imenu_client.service;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.gs.collections.api.tuple.Pair;
import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.LoginActivity;
import com.sparta.imenu_client.model.User;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Hussein Abu Maash on 3/20/2016.
 */

public class SignUpService extends AsyncTask<Void, Void, Boolean> {
    LoginActivity context;
    User newUser;

    public SignUpService(LoginActivity context, User newUser) {
        this.context=context;
        this.newUser=newUser;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final String url = context.getString(R.string.url)+"user/authenticate";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Log.i("LoginActivity", newUser.toString());
        boolean result = restTemplate.postForObject(url, newUser, boolean.class);
        Log.i("LoginActivity"," Sign Up button pressed");
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
