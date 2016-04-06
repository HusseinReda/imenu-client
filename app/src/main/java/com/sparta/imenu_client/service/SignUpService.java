package com.sparta.imenu_client.service;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
    Exception error;

    public SignUpService(LoginActivity context, User newUser) {
        this.context=context;
        this.newUser=newUser;
    }

    ///should check for internet connectivity
    @Override
    protected Boolean doInBackground(Void... params) {
        final String url = context.getString(R.string.url)+"user/add";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Log.i("LoginActivity", newUser.toString());
        boolean result;
        try {
            result = restTemplate.postForObject(url, newUser, boolean.class);
            Log.i("LoginActivity"," Sign Up button pressed");
            return result;
        }
        catch(Exception e) {
            error = e;
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(error!=null)
            Toast.makeText(context,"There is a problem in the server\nPlease try again later",Toast.LENGTH_LONG).show();
        else
            if (result)
                Toast.makeText(context, "Sign Up is successful\nYou can now Log in!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "Sign Up is not successful\nPlease try again later", Toast.LENGTH_LONG).show();
        Intent loginIntent = new Intent(context, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(loginIntent);
    }
}
