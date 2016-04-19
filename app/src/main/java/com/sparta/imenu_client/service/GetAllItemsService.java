package com.sparta.imenu_client.service;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationConfig;
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
import java.util.Arrays;
import java.util.List;

/**
 * Created by Hussein Abu Maash on 4/6/2016.
 */

public class GetAllItemsService extends AsyncTask<Void, Void, List<Item>> {
    HomeActivity context;
    RecyclerView recyclerView;
    Exception error;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    public GetAllItemsService() {
    }

    public GetAllItemsService(HomeActivity context,RecyclerView recyclerView,
                              ProgressBar progressBar,SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.recyclerView=recyclerView;
        this.progressBar = progressBar;
        this.swipeRefreshLayout=swipeRefreshLayout;
    }
    @Override
    protected void onPreExecute() {

        context.setProgressBarIndeterminateVisibility(true);
    }
    @Override
    protected List<Item> doInBackground(Void... params) {
        Log.i("getAll Act", "service started");
//        Log.i("recommended Act", email);
        final String url = context.getString(R.string.url)+"item/getAll";
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
        Log.i("getAll service", String.valueOf(items.size()));
        if(items.size()!=0){
            Log.i("getAll service", String.valueOf(items.size())+" 2");
            List<Item> itemsss=new ArrayList<Item>();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            for(int i=0;i<items.size();i++){
                Item itemmm = mapper.convertValue(items.get(i), Item.class);
                itemsss.add(itemmm);
            }
            ItemRecyclerViewAdapter adapter= new ItemRecyclerViewAdapter(itemsss,context);
            recyclerView.setAdapter(adapter);
            Log.i("getAll service", String.valueOf(items.size()) + " 4");
        }
        else {
            Toast.makeText(context, "There is a problem in the server\nPlease try again later", Toast.LENGTH_LONG).show();
        }
        swipeRefreshLayout.setRefreshing(false);
    }
}
