package com.sparta.imenu_client.service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.node.IntNode;
import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.HomeActivity;
import com.sparta.imenu_client.model.User;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Hussein Abu Maash on 5/4/2016.
 */

public class UpdateUserService extends AsyncTask<Void, Void, Boolean> {

    Context context;
    User newUser;

    public UpdateUserService(Context context, User newUser) {
        this.context = context;
        this.newUser = newUser;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final String url = context.getString(R.string.url)+"user/updateUser";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        Log.i("update service", "before submit : " + newUser.getHealthIssues().size());
        Log.i("update service", "before submit : " + newUser.getRestrictions().size());
        Log.i("update service", "before submit : " + newUser.getPassword());
        boolean result = restTemplate.postForObject(url,newUser,Boolean.class);
        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result){
            Toast.makeText(context, "Changes updated successfully !", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            ((AppCompatActivity)context).finish();

        }
        else
            Toast.makeText(context,"Updating failed!\nPlease try again later",Toast.LENGTH_LONG).show();
    }
}
