package com.sparta.imenu_client.activity;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;


import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.ConnectionRequest;
import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.model.Order;
import com.sparta.imenu_client.model.OrderCard;
import com.sparta.imenu_client.service.AddOrderService;
import com.sparta.imenu_client.service.Auxiliary;
import com.sparta.imenu_client.service.CallWaiterService;
import com.sparta.imenu_client.service.ConnectToRestaurantService;
import com.sparta.imenu_client.service.DisconnectToRestaurantService;
import com.sparta.imenu_client.service.GetAllUserSpecService;
import com.sparta.imenu_client.service.GetRecommendedService;
import com.sparta.imenu_client.service.GetRestaurantByNameService;
import com.sparta.imenu_client.service.GetUserByEmailService;
import com.sparta.imenu_client.userInterface.ItemRecyclerViewAdapter;
import com.sparta.imenu_client.userInterface.LogoutDialog;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    String email;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    MenuItem orderMenuItem;
    MenuItem connectMenuItem;
    MenuItem disconnectMenuItem;
    MenuItem callWaiterMenuItem;
    MenuItem restaurantMenuItem;
    private FragmentManager fm;
    private OrderDialogFragment orderDialogFragment;

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
        List<Item> tempItems = new ArrayList<>();
        ItemRecyclerViewAdapter adapter= new ItemRecyclerViewAdapter(tempItems,this,null);
        recyclerView.setAdapter(adapter);

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
        orderMenuItem = menu.findItem(R.id.action_order);
        connectMenuItem = menu.findItem(R.id.action_connect_to_restaurant);
        disconnectMenuItem = menu.findItem(R.id.action_disconnect_from_restaurant);
        callWaiterMenuItem = menu.findItem(R.id.action_call_waiter);
        restaurantMenuItem = menu.findItem(R.id.action_connected_restaurant);
        refreshMenu();
        return true;
    }

    public void refreshMenu() {
        if(Auxiliary.connectedToRest==null){
            orderMenuItem.setVisible(false);
            callWaiterMenuItem.setVisible(false);
            disconnectMenuItem.setVisible(false);
            restaurantMenuItem.setVisible(false);
            connectMenuItem.setVisible(true);
        }
        else {
            orderMenuItem.setVisible(true);
            callWaiterMenuItem.setVisible(true);
            disconnectMenuItem.setVisible(true);
            restaurantMenuItem.setVisible(true);
            connectMenuItem.setVisible(false);
        }
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

            case R.id.action_connect_to_restaurant:
                connectionDialog();
                Log.i("home", "connect clicked");
                refreshMenu();
                return true;

            case R.id.action_disconnect_from_restaurant:
                disconnectDialog();
                return true;

            case R.id.action_call_waiter:
                CallWaiterService callWaiterService = new CallWaiterService(this,Auxiliary.serviceTableSecretNumber);
                callWaiterService.execute();
                return true;

            case R.id.action_order:
                Log.i("home", "order clicked");
//                List<OrderCard> orderCards = new ArrayList<OrderCard>();
//                Item item1 = new Item("Mighty Bucket Meal","KFC","chicken",48.18,"2 pieces chicken + 3 pieces chicken strips + small French fries + small coleslaw salad + bun + garlic mayonnaise sauce + dynamite sauce + soft drink",null,null);
//                item1.setId(1);
//                OrderCard orderCard = new OrderCard(item1,4,2);
//                orderCards.add(orderCard);
//                Order order = new Order(2,Auxiliary.getCurrentUser(),orderCards,1);
//                AddOrderService addOrderService = new AddOrderService(this,order);
//                addOrderService.execute();
                fm = getSupportFragmentManager();
                orderDialogFragment = OrderDialogFragment.newInstance("Order");
                orderDialogFragment.show(fm, "order_dialog_layout");

                return true;

            case R.id.action_connected_restaurant:
                GetRestaurantByNameService getRestaurantByNameService = new GetRestaurantByNameService(this,Auxiliary.connectedToRest);
                getRestaurantByNameService.execute();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void disconnectDialog() {
        AlertDialog.Builder disconnectDialogBuilder = new AlertDialog.Builder(this);
        disconnectDialogBuilder.setMessage(R.string.dialog_disconnect);
        disconnectDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                ConnectionRequest connectionRequest = new ConnectionRequest(Auxiliary.getCurrentUser(),Auxiliary.serviceTableSecretNumber);
                DisconnectToRestaurantService disconnectToRestaurantService = new DisconnectToRestaurantService(HomeActivity.this,connectionRequest);
                try {
                    disconnectToRestaurantService.execute().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshMenu();
                if(Auxiliary.connectedToRest==null)
                    Auxiliary.order=null;
            }
        });
        disconnectDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog disconnectDialog = disconnectDialogBuilder.create();
        disconnectDialog.show();
    }

    private void connectionDialog() {
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.connection_dialog);
        dialog.setTitle("Connect to restaurant");

        TextView messageText = (TextView) dialog.findViewById(R.id.connection_message_text);
        messageText.setText("Enter the table's secret number");
        final EditText connectionText = (EditText) dialog.findViewById(R.id.connection_text);
        Button dialogButton = (Button) dialog.findViewById(R.id.connection_button);

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int secretNumber=Integer.parseInt(connectionText.getText().toString());
                ConnectionRequest connectionRequest = new ConnectionRequest(Auxiliary.getCurrentUser(),secretNumber);
                ConnectToRestaurantService connectToRestaurantService = new ConnectToRestaurantService(HomeActivity.this,
                        connectionRequest);
                try {
                    connectToRestaurantService.execute().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                refreshMenu();
            }
        });

        dialog.show();
    }

    @Override
    public void onRefresh() {
        GetRecommendedService getRecommendedService = new GetRecommendedService(this,
                recyclerView,progressBar,swipeRefreshLayout);
        getRecommendedService.execute();
    }
}

