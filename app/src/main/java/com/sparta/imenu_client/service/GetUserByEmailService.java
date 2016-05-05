package com.sparta.imenu_client.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.ProfileActivity;
import com.sparta.imenu_client.model.User;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

/**
 * Created by Hussein Abu Maash on 5/3/2016.
 */

public class GetUserByEmailService extends AsyncTask<Void,Void,User>{
    Context context;
    String email;
    User currentUser;

    public GetUserByEmailService(Context context) {
        this.context = context;
        SharedPreferences currentUserPref = context.getApplicationContext()
                .getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        email = currentUserPref.getString("email",null);
    }

    @Override
    protected User doInBackground(Void... params) {
        final String url = context.getString(R.string.url)+"user/email?email="+email;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        currentUser = restTemplate.getForObject(url,User.class);
        return currentUser;
    }

    @Override
    protected void onPostExecute(User user) {
        Intent profileIntent = new Intent(context, ProfileActivity.class);
        profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        profileIntent.putExtra("currentUser",user);
        context.getApplicationContext().startActivity(profileIntent);
    }
}
