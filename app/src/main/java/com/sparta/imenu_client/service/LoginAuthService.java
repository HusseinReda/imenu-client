package com.sparta.imenu_client.service;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.HomeActivity;
import com.sparta.imenu_client.activity.LoginActivity;
import com.sparta.imenu_client.model.UserRequest;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Hussein Abu Maash on 3/10/2016.
 */

public class LoginAuthService extends AsyncTask<Void, Void, Boolean> {
    LoginActivity context;
    UserRequest userRequest;
    Exception error;

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
        boolean result;
        try {
            result = restTemplate.postForObject(url, userRequest, boolean.class);
            Log.i("LoginActivity","button pressed");
            return result;
        }
        catch (Exception e){
            error=e;
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(error!=null){
            Toast.makeText(context,"There is a problem in the server\nPlease try again later",Toast.LENGTH_LONG).show();
            Intent loginIntent= new Intent(context, LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(loginIntent);
        }
        else {
            if (result) {
                Toast.makeText(context, "Log in successful", Toast.LENGTH_LONG).show();
                Intent homeIntent = new Intent(context, HomeActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                homeIntent.putExtra("username",userRequest.getEmail());
                context.getApplicationContext().startActivity(homeIntent);
            } else {
                Toast.makeText(context, "Incorrect email or password", Toast.LENGTH_LONG).show();
                Intent loginIntent = new Intent(context, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(loginIntent);
            }
        }
    }
}