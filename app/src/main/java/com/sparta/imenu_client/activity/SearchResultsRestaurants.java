package com.sparta.imenu_client.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.model.Restaurant;
import com.sparta.imenu_client.service.GetRecommendedService;
import com.sparta.imenu_client.service.SearchRestaurantService;
import com.sparta.imenu_client.userInterface.ItemRecyclerViewAdapter;
import com.sparta.imenu_client.userInterface.RestaurantRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsRestaurants extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RestaurantRecyclerViewAdapter adapter;
    private String query;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public SearchResultsRestaurants() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle extras = getArguments();
        query=extras.getString("query");

        View view= inflater.inflate(R.layout.fragment_search_results_restaurants,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.restaurant_search_recycler_view);
        linearLayoutManager=new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        adapter= new RestaurantRecyclerViewAdapter(new ArrayList<Restaurant>(),getContext());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.search_restaurant_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        SearchRestaurantService searchRestaurantService = new SearchRestaurantService(this,query,recyclerView,progressBar,swipeRefreshLayout);
        searchRestaurantService.execute();
        return view;
    }

    @Override
    public void onRefresh() {
        SearchRestaurantService searchRestaurantService = new SearchRestaurantService(this,query,recyclerView,progressBar,swipeRefreshLayout);
        searchRestaurantService.execute();
    }
}
