package com.sparta.imenu_client.service;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.HomeActivity;
import com.sparta.imenu_client.activity.SearchResults;
import com.sparta.imenu_client.activity.SearchResultsItems;
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

public class SearchItemService extends AsyncTask<Void, Void, List<Item>> {
    ProgressBar progressBar;
    SearchResultsItems context;
    String query;
    RecyclerView recyclerView;
    Exception error;
    SwipeRefreshLayout swipeRefreshLayout;

    public SearchItemService() {
    }

    public SearchItemService(SearchResultsItems context, String query,
                             RecyclerView recyclerView,ProgressBar progressBar,SwipeRefreshLayout swipeRefreshLayout) {
        this.recyclerView=recyclerView;
        this.context = context;
        this.query = query;
        this.progressBar = progressBar;
        this.swipeRefreshLayout=swipeRefreshLayout;
    }
    @Override
    protected void onPreExecute() {
        context.getActivity().setProgressBarIndeterminateVisibility(true);
    }
    @Override
    protected List<Item> doInBackground(Void... params) {
        Log.i("search meals Act", "service started");
        final String url = context.getString(R.string.url)+"item/search?query="+query;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        try {
            Log.i("search meals act","el query: "+query+" ##");
            List<Item> items = restTemplate.getForObject(url, List.class);
            Log.i("search meals act","button pressed");
            Log.i("search meals act", String.valueOf(items.size()));
            return items;
        }
        catch (Exception e){
            error=e;
            return null;
        }
    }
    @Override
    protected void onPostExecute(List<Item> items) {
        if(items==null) {
            Toast.makeText(context.getContext(),"There is a problem in the server\nPlease try again later",Toast.LENGTH_LONG).show();
        }
        else {
            Log.i("search meals act", String.valueOf(items.size()));
            progressBar.setVisibility(View.GONE);
            // recyclerView.getAdapter().notifyDataSetChanged();
            if (items.size() != 0) {
                //callingActivity.updateRecycleView(Arrays.asList(items));
                //            recyclerView.getAdapter().notifyDataSetChanged();
                Log.i("search item service", String.valueOf(items.size()) + " 2");
                List<Item> itemsss = new ArrayList<Item>();
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                for (int i = 0; i < items.size(); i++) {
                    Item itemmm = mapper.convertValue(items.get(i), Item.class);
                    itemsss.add(itemmm);
                }
                ItemRecyclerViewAdapter adapter = new ItemRecyclerViewAdapter(itemsss, context.getActivity());
                recyclerView.setAdapter(adapter);
                Log.i("search item service", String.valueOf(items.size()) + " 4");
            } else {
                Toast.makeText(context.getActivity().getBaseContext(), "Sorry, no food available with keyword:\n" + query, Toast.LENGTH_LONG).show();
            }
        }
        swipeRefreshLayout.setRefreshing(false);
    }

}
