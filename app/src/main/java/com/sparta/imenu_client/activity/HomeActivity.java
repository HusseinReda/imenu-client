package com.sparta.imenu_client.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;


import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.service.GetAllUserSpecService;
import com.sparta.imenu_client.service.GetRecommendedService;
import com.sparta.imenu_client.service.GetUserByEmailService;
import com.sparta.imenu_client.userInterface.ItemRecyclerViewAdapter;
import com.sparta.imenu_client.userInterface.LogoutDialog;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    String email;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public HomeActivity() {
    }

    public HomeActivity(String email) {
        this.email = email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GetAllUserSpecService getAllUserSpecService = new GetAllUserSpecService(this);
        getAllUserSpecService.execute();

        GetUserByEmailService getUserByEmailService = new GetUserByEmailService(this,false);
        getUserByEmailService.execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        setUpSearch();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.home_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        SharedPreferences currentUserPref = getApplicationContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        email = currentUserPref.getString("email",null);

        progressBar = (ProgressBar) findViewById(R.id.home_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        //recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
//
//        ItemRecyclerViewAdapter adapter= new ItemRecyclerViewAdapter(items,this);
//        recyclerView.setAdapter(adapter);

        GetRecommendedService getRecommendedService = new GetRecommendedService(this,recyclerView,progressBar,swipeRefreshLayout);
        getRecommendedService.execute();
        Log.i("recommend service", " 3");

    }

    private void setUpSearch() {
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(new ComponentName(this,SearchResults.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.template_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_profile:
                GetUserByEmailService getUserByEmailService = new GetUserByEmailService(this,true);
                getUserByEmailService.execute();
                return true;

            case R.id.action_logout:
                LogoutDialog logoutDialog = new LogoutDialog(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        GetRecommendedService getRecommendedService = new GetRecommendedService(this,
                recyclerView,progressBar,swipeRefreshLayout);
        getRecommendedService.execute();
    }
}
