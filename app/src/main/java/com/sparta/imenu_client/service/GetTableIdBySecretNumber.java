package com.sparta.imenu_client.service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.sparta.imenu_client.R;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Hussein Abu Maash on 6/17/2016.
 */

public class GetTableIdBySecretNumber extends AsyncTask<Void,Void,Long> {
    private int secretNumber;
    private Context context;
    Exception error;

    public GetTableIdBySecretNumber(Context context) {
        this.secretNumber=Auxiliary.serviceTableSecretNumber;
        this.context=context;
    }

    @Override
    protected Long doInBackground(Void... params) {
        Log.i("getTable service", "service started");
        Log.i("getTable service", "table secret number= "+secretNumber);
        ///////////////////
        final String url = context.getString(R.string.url)+"table/secretNumber?secretNumber="+secretNumber;
        //////////////////
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

        long tableId= restTemplate.getForObject(url, Long.class);
        Log.i("getTable service","Id = "+tableId);
        return tableId;

    }

    @Override
    protected void onPostExecute(Long tableId) {
        Auxiliary.order.setServiceTableId(tableId);
    }
}
