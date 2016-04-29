package com.sparta.imenu_client.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.HomeActivity;
import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.userInterface.ItemRecyclerViewAdapter;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hussein Abu Maash on 4/6/2016.
 */

public class GetRecommendedService extends AsyncTask<Void, Void, List<Item>> {
    HomeActivity context;
    String email;
    RecyclerView recyclerView;
    Exception error;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    public GetRecommendedService() {
    }

    public GetRecommendedService(HomeActivity context, RecyclerView recyclerView,
                                 ProgressBar progressBar,SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.recyclerView=recyclerView;
        this.progressBar = progressBar;
        this.swipeRefreshLayout=swipeRefreshLayout;
        SharedPreferences currentUserPref = context.getApplicationContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        email = currentUserPref.getString("email",null);
    }
    @Override
    protected void onPreExecute() {
        context.setProgressBarIndeterminateVisibility(true);
    }
    @Override
    protected List<Item> doInBackground(Void... params) {
        Log.i("recommended Act", "service started");
//        Log.i("recommended Act", email);
        final String url = context.getString(R.string.url)+"user/recommend?email="+ email;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        try {
            List<Item> items = restTemplate.getForObject(url, List.class);
            return items;
        }
        catch (Exception e){
            error=e;
            Log.i("recommend service","error caught");
            return null;
        }
    }
    @Override
    protected void onPostExecute(List<Item> items) {

        progressBar.setVisibility(View.GONE);
        Log.i("recommend service", String.valueOf(items.size()));
        if(items.size()!=0){
            Log.i("recommend service", String.valueOf(items.size())+" 2");
            List<Item> itemsss=new ArrayList<Item>();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            for(int i=0;i<items.size();i++){
                Item itemmm = mapper.convertValue(items.get(i), Item.class);
                itemsss.add(itemmm);
            }
            ItemRecyclerViewAdapter adapter= new ItemRecyclerViewAdapter(itemsss,context);
            recyclerView.setAdapter(adapter);
            Log.i("recommend service", String.valueOf(items.size()) + " 4");
        }
        else {
            GetAllItemsService getAllItemsService= new GetAllItemsService(context,recyclerView,progressBar,swipeRefreshLayout);
            getAllItemsService.execute();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

}
