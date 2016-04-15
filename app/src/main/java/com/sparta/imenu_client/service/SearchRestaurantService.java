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
import com.sparta.imenu_client.activity.SearchResultsItems;
import com.sparta.imenu_client.activity.SearchResultsRestaurants;
import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.model.Restaurant;
import com.sparta.imenu_client.userInterface.ItemRecyclerViewAdapter;
import com.sparta.imenu_client.userInterface.RestaurantRecyclerViewAdapter;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hussein Abu Maash on 4/6/2016.
 */

public class SearchRestaurantService extends AsyncTask<Void, Void, List<Restaurant> >{
    SearchResultsRestaurants context;
    String query;
    Exception error;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    public SearchRestaurantService() {
    }

    public SearchRestaurantService(SearchResultsRestaurants context, String query,
                                   RecyclerView recyclerView,ProgressBar progressBar,SwipeRefreshLayout swipeRefreshLayout) {

        this.context = context;
        this.query = query;
        this.recyclerView=recyclerView;
        this.progressBar = progressBar;
        this.swipeRefreshLayout=swipeRefreshLayout;
    }
    @Override
    protected void onPreExecute() {
        context.getActivity().setProgressBarIndeterminateVisibility(true);
    }
    @Override
    protected List<Restaurant> doInBackground(Void... params) {
        Log.i("search meals Act", "service started");
        final String url = context.getString(R.string.url)+"restaurant/searchNoMenu?query="+query;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        try {
            List<Restaurant> result = restTemplate.getForObject(url, List.class);
            Log.i("search meals act","button pressed");
            return result;
        }
        catch (Exception e){
            error=e;
            return null;
        }
    }
    @Override
    protected void onPostExecute(List<Restaurant> result) {
        if(result==null) {
            Toast.makeText(context.getContext(),"There is a problem in the server\nPlease try again later",Toast.LENGTH_LONG).show();
        }
        else {
            Log.i("search rest service", String.valueOf(result.size()));
            progressBar.setVisibility(View.GONE);
            // recyclerView.getAdapter().notifyDataSetChanged();
            if (result.size() != 0) {
                //context.updateRecycleView(Arrays.asList(items));
                //            recyclerView.getAdapter().notifyDataSetChanged();
                Log.i("search rest service", String.valueOf(result.size()) + " 2");
                List<Restaurant> restaurants = new ArrayList<Restaurant>();
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                for (int i = 0; i < result.size(); i++) {
                    Restaurant restaurant = mapper.convertValue(result.get(i), Restaurant.class);
                    restaurants.add(restaurant);
                }
                RestaurantRecyclerViewAdapter adapter = new RestaurantRecyclerViewAdapter(restaurants, context.getActivity());
                recyclerView.setAdapter(adapter);
                Log.i("search item service", String.valueOf(restaurants.size()) + " 4");
            } else {
                Toast.makeText(context.getActivity().getBaseContext(), "Sorry, no restaurants available with name:\n" + query, Toast.LENGTH_LONG).show();
            }
        }
        swipeRefreshLayout.setRefreshing(false);
    }

}
