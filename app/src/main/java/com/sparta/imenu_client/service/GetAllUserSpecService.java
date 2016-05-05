package com.sparta.imenu_client.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.UserSpec;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

/**
 * Created by Hussein Abu Maash on 5/2/2016.
 */

public class GetAllUserSpecService extends AsyncTask<Void,Void,UserSpec[] > {
    Context context;
    UserSpec[] userSpecs;

    public GetAllUserSpecService(Context context) {
        this.context = context;
    }

    @Override
    protected UserSpec[] doInBackground(Void... params) {
        final String url = context.getString(R.string.url)+"userSpec/getAll";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        userSpecs = restTemplate.getForObject(url,UserSpec[].class);
        return userSpecs;
    }

    @Override
    protected void onPostExecute(UserSpec[] userSpec) {
        Log.i("userspec serv","before if");
        if(userSpec!=null && userSpec.length!=0) {
            Log.i("userspec serv", String.valueOf(userSpec.length));
            for(int i=0;i<userSpec.length;i++){
                if(userSpec[i].getType()==true) {
                    Auxiliary.addHealthIssue(userSpec[i]);
                    Log.i("userspec serv", "healthIssue added");
                }
                else{
                    Auxiliary.addRestriction(userSpec[i]);
                    Log.i("userspec serv", "rest added");
                }
            }
        }
    }
}
