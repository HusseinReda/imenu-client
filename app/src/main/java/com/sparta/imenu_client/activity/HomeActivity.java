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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;


import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.service.GetRecommendedService;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        setUpSearch();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.home_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        SharedPreferences currentUserPref = getApplicationContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        email = currentUserPref.getString("email",null);

        ///meals hardcoding
        String names[] = {"Mighty Bucket Meal","Mighty Popcorn Meal","My Meal","Super Snack Meal","Super Snack Meal",
                "Dinner Box","Dinner Box","Extra Chicken Piece"};
        String urls[]={"https://assets.otlob.com/dynamic/images/products/62/62574_1453380791_ma.png",
                "https://assets.otlob.com/dynamic/images/products/62/62579_1453380790_ma.png",
                "https://assets.otlob.com/dynamic/images/products/62/62580_1453387656_ma.jpg",
                "https://assets.otlob.com/dynamic/images/products/62/62587_1453387656_ma.jpg",
                "https://assets.otlob.com/dynamic/images/products/62/62588_1442482035_ma.jpg",
                "https://assets.otlob.com/dynamic/images/products/62/62612_1448372552_ma.jpg",
                "https://assets.otlob.com/dynamic/images/products/62/62613_1448372552_ma.jpg",
                "https://assets.otlob.com/dynamic/images/products/62/62614_1453381076_ma.jpg",
        };
        double prices[] ={48.18,50.00,10.45,23.64,18.64,31.82,39.54,9.54};
        String descriptions[]={"2 pieces chicken + 3 pieces chicken strips + small French fries + small coleslaw salad + bun + garlic mayonnaise sauce + dynamite sauce + soft drink",
                "2 pieces chicken + 2 pieces chicken strips + 10 popcorn + small French fries + small coleslaw salad + bun + garlic mayonnaise sauce + dynamite sauce + soft drink",
                "one chicken piece, served with small French fries + 2 bun bread",
                "2 pieces chicken, served with large rice + small French fries + bun",
                "2 pieces fried chicken + small French fries + bun",
                "3 pieces fried chicken + small French fries + coleslaw salad + bun",
                "3 pieces fried chicken + medium French fries + coleslaw salad + bun",
                "a"};

        ArrayList<String> keywords[] = new ArrayList[]{new ArrayList<String>(Arrays.asList("chicken", "strips", "fries",
                "coleslaw", "salad", "bun", "garlic mayonnaise sauce", "dynamite sauce", "drink", "bread")),
                new ArrayList<String>(Arrays.asList("chicken","strips","popcorn","fries","coleslaw","bun","garlic mayonnaise sauce","dynamite sauce","drink","bread")),
                new ArrayList<String>(Arrays.asList("chicken","one piece","fries","bun","bread")),
                new ArrayList<String>(Arrays.asList("chicken","2 pieces","rice","large rice","fries","bun","bread")),
                new ArrayList<String>(Arrays.asList("chicken","2 pieces","fries","bun","bread")),
                new ArrayList<String>(Arrays.asList("chicken","3 pieces","fries","small fries","coleslaw","salad","bun","bread")),
                new ArrayList<String>(Arrays.asList("chicken","3 pieces","fries","medium fries","coleslaw","salad","bun","bread")),
                new ArrayList<String>(Arrays.asList("chicken","1 pieces"))
        };
        ArrayList<Item> items = new ArrayList<Item>();
        for (int i=0;i<names.length;i++) {
            items.add(new Item(names[i],"KFC", "chicken", prices[i], descriptions[i], keywords[i], urls[i]));
        }
        ///

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        //recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        ItemRecyclerViewAdapter adapter= new ItemRecyclerViewAdapter(items,this);
        recyclerView.setAdapter(adapter);

//        GetRecommendedService getRecommendedService = new GetRecommendedService(this,recyclerView,progressBar,swipeRefreshLayout);
//        getRecommendedService.execute();
//        Log.i("recommend service", " 3");

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
                Intent profileIntent = new Intent(this, ProfileActivity.class);
                this.getApplicationContext().startActivity(profileIntent);
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
