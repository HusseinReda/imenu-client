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
import com.sparta.imenu_client.service.GetRecommendedService;
import com.sparta.imenu_client.service.SearchItemService;
import com.sparta.imenu_client.userInterface.ItemRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchResultsItems extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ItemRecyclerViewAdapter adapter;
    private String query;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public SearchResultsItems() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle extras = getArguments();
        query=extras.getString("query");

        View view= inflater.inflate(R.layout.fragment_search_results_items,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.item_search_recycler_view);
        linearLayoutManager=new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        adapter= new ItemRecyclerViewAdapter(new ArrayList<Item>(),getContext(),null);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.search_item_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        SearchItemService searchItemService = new SearchItemService(this,query,recyclerView,progressBar,swipeRefreshLayout);
        searchItemService.execute();
        return view;
    }

    @Override
    public void onRefresh() {
        SearchItemService searchItemService = new SearchItemService(this,query,recyclerView,progressBar,swipeRefreshLayout);
        searchItemService.execute();
    }

}
