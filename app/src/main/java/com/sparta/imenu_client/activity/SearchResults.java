package com.sparta.imenu_client.activity;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.ConnectionRequest;
import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.model.Order;
import com.sparta.imenu_client.model.OrderCard;
import com.sparta.imenu_client.service.Auxiliary;
import com.sparta.imenu_client.service.CallWaiterService;
import com.sparta.imenu_client.service.ConnectToRestaurantService;
import com.sparta.imenu_client.service.DisconnectToRestaurantService;
import com.sparta.imenu_client.service.GetRestaurantByNameService;
import com.sparta.imenu_client.service.GetUserByEmailService;
import com.sparta.imenu_client.userInterface.LogoutDialog;

import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String query;
    private SearchResultsItems searchResultsItems;
    private SearchResultsRestaurants searchResultsRestaurants;
    MenuItem orderMenuItem;
    MenuItem connectMenuItem;
    MenuItem disconnectMenuItem;
    MenuItem callWaiterMenuItem;
    MenuItem restaurantMenuItem;
    private FragmentManager fm;
    private OrderDialogFragment orderDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);

        setUpSearch();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        handleIntent(getIntent());
    }

    private void setupViewPager(ViewPager viewPager) {
        handleIntent(getIntent());
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Log.i("search results", "el query: " + query + " ##");

        /////
        TextView querySearchedFor = (TextView)findViewById(R.id.query_searched_for);
        querySearchedFor.setText("You have searched for: "+query);
        /////

        Bundle bundle = new Bundle();
        bundle.putString("query",query);
        searchResultsItems=new SearchResultsItems();
        searchResultsRestaurants=new SearchResultsRestaurants();
        searchResultsItems.setArguments(bundle);
        searchResultsRestaurants.setArguments(bundle);

        adapter.addFragment(searchResultsItems, "Food");
        adapter.addFragment(searchResultsRestaurants, "Restaurant");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setUpSearch() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
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

    private void refreshMenu() {
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
                Log.i("home","connect clicked");
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
                List<OrderCard> orderCards = new ArrayList<OrderCard>();
                Item item1 = new Item("Mighty Bucket Meal","KFC","chicken",48.18,"2 pieces chicken + 3 pieces chicken strips + small French fries + small coleslaw salad + bun + garlic mayonnaise sauce + dynamite sauce + soft drink",null,null);
                item1.setId(1);
                OrderCard orderCard = new OrderCard(item1,4,2);
                orderCards.add(orderCard);
                Order order = new Order(2,Auxiliary.getCurrentUser(),orderCards,1);
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

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            //Toast.makeText(SearchResults.this, query, Toast.LENGTH_SHORT).show();
        }
    }

    private void connectionDialog() {
        final Dialog dialog = new Dialog(SearchResults.this);
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
                ConnectToRestaurantService connectToRestaurantService = new ConnectToRestaurantService(SearchResults.this,
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
    private void disconnectDialog() {
        AlertDialog.Builder disconnectDialogBuilder = new AlertDialog.Builder(this);
        disconnectDialogBuilder.setMessage(R.string.dialog_disconnect);
        disconnectDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                ConnectionRequest connectionRequest = new ConnectionRequest(Auxiliary.getCurrentUser(), Auxiliary.serviceTableSecretNumber);
                DisconnectToRestaurantService disconnectToRestaurantService = new DisconnectToRestaurantService(SearchResults.this, connectionRequest);
                try {
                    disconnectToRestaurantService.execute().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refreshMenu();
                if (Auxiliary.connectedToRest == null)
                    Auxiliary.order = null;
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
}
