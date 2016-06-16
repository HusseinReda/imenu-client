package com.sparta.imenu_client.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.Order;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Hussein Abu Maash on 6/14/2016.
 */

public class AddOrderService extends AsyncTask<Void,Void,Void> {
    Context context;
    Order order;

    public AddOrderService(Context context, Order userSpec) {
        this.context = context;
        this.order = userSpec;
    }

    @Override
    protected Void doInBackground(Void... params) {
        final String url = context.getString(R.string.url)+"order/add";
        RestTemplate restTemplate=  new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        Log.i("order service",Integer.toString(order.getOrderCardList().size()));
        String response = restTemplate.postForObject(url, order,String.class);
        Log.i("order service","order sent");
        return null;
    }
}
