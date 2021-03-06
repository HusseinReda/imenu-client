package com.sparta.imenu_client.activity;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.ConnectionRequest;
import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.model.Order;
import com.sparta.imenu_client.model.OrderCard;
import com.sparta.imenu_client.service.AddRateService;
import com.sparta.imenu_client.service.Auxiliary;
import com.sparta.imenu_client.service.CallWaiterService;
import com.sparta.imenu_client.service.ConnectToRestaurantService;
import com.sparta.imenu_client.service.DisconnectToRestaurantService;
import com.sparta.imenu_client.service.GetRestaurantByNameService;
import com.sparta.imenu_client.service.GetUserByEmailService;
import com.sparta.imenu_client.userInterface.LogoutDialog;
import com.sparta.imenu_client.userInterface.IMenuAnimation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ItemActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Item item;
    TextView ingredientsData;
    ImageView itemIngredientsLayoutImg;
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
        setContentView(R.layout.activity_item);
        Intent myIntent = getIntent();
        item = (Item) myIntent.getSerializableExtra("item");

        // Issues notes
        String issuesNotes = getIssuesNotes();
        if(!issuesNotes.equals("")){
            showIssuesNotesDialog(issuesNotes);
        }

        itemIngredientsLayoutImg = (ImageView) findViewById(R.id.item_ingredients_layout_img);

        Toolbar toolbar = (Toolbar) findViewById(R.id.item_toolbar);
        setSupportActionBar(toolbar);

        final Button btn1 = (Button) findViewById(R.id.add_item_rating_button);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showRatingDialog();
            }
        });

        ingredientsData = (TextView) findViewById(R.id.item_ingredients_data);
        ingredientsData.setVisibility(View.GONE);

        bindData(item);

    }

    private String getIssuesNotes() {
        HashSet<String> notes = new HashSet<>();
        ArrayList<String> returnedNotes = new ArrayList<String>();
        String printedNote="";

        // Restrictions
        for(int i=0;i<item.getKeywords().size();i++){
            ArrayList<String> temp = Auxiliary.searchInAllRestrictions(Auxiliary.getCurrentUser(), item.getKeywords().get(i));
            for(int c=0;c<temp.size();c++)
                returnedNotes.add(temp.get(c));
        }

        // Health Issues
        for(int i=0;i<item.getKeywords().size();i++){
            ArrayList<String> temp = Auxiliary.searchInAllHealthIssues(Auxiliary.getCurrentUser(),item.getKeywords().get(i));
            for(int c=0;c<temp.size();c++)
                returnedNotes.add(temp.get(c));
        }

        Log.i("returned", String.valueOf(returnedNotes.size()));
        for(int i=0;i<returnedNotes.size();i++)
            notes.add(returnedNotes.get(i));

        Log.i("notes", String.valueOf(notes.size()));
        for (String note : notes) {
            Log.i("note",note);
            printedNote+="- "+note+"\n";
        }

        return printedNote;
    }

    private void showIssuesNotesDialog(String issuesNotes) {
        final AlertDialog.Builder issuesNotesDialog = new AlertDialog.Builder(this);

        issuesNotesDialog.setCancelable(true);
        issuesNotesDialog.setTitle(R.string.warning);
        issuesNotesDialog.setIcon(android.R.drawable.ic_dialog_alert);
        issuesNotesDialog.setMessage(issuesNotes);

        issuesNotesDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = issuesNotesDialog.create();
        alertDialog.show();
    }

//    private void setUpSearch() {
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) findViewById(R.id.search_view);
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(new ComponentName(this, SearchResults.class)));
//    }

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


    private void bindData(Item item) {
        ImageView itemImage = (ImageView) findViewById(R.id.item_image);
        TextView itemName = (TextView) findViewById(R.id.item_name);
        TextView itemRestName = (TextView) findViewById(R.id.item_restaurant_name);
        TextView itemDescription = (TextView) findViewById(R.id.item_description);
        TextView itemPrice = (TextView) findViewById(R.id.item_price);
        TextView itemRating = (TextView) findViewById(R.id.item_rating);

        Picasso.with(this).load(item.getPicture())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.loading_image)
                .into(itemImage);
        itemName.setText(item.getName());
        itemRestName.setText(item.getRestaurantName());
        itemDescription.setText(item.getDescription());
        itemPrice.setText("Price : "  + Double.toString(item.getPrice()) + " LE");
        itemRating.setText("Rating : " + Double.toString(item.getRating()));

        ArrayList<String> ingredients = new ArrayList<>();

        for(int i=0;i<item.getKeywords().size();i++){
            if(Character.isUpperCase(item.getKeywords().get(i).charAt(0)))
                ingredients.add(item.getKeywords().get(i));

        }

        String tempIngredients="";
        for(int i=0;i<ingredients.size();i++){
            tempIngredients+="- "+ingredients.get(i);
            tempIngredients+="\n";
        }
        ingredientsData.setText(tempIngredients);
        Log.i("Item Act", "ingredients size " + ingredients.size());
    }

    public void toggle_contents(View v){
        if(ingredientsData.isShown()){
            IMenuAnimation.slide_up(this, ingredientsData);
            ingredientsData.setVisibility(View.GONE);
            itemIngredientsLayoutImg.setImageResource(R.drawable.plus_icon);
        }
        else{
            ingredientsData.setVisibility(View.VISIBLE);
            IMenuAnimation.slide_down(this, ingredientsData);
            itemIngredientsLayoutImg.setImageResource(R.drawable.minus_icon);
        }
    }

    public void showRatingDialog() {
        final AlertDialog.Builder ratingDialog = new AlertDialog.Builder(this);

        ratingDialog.setCancelable(true);
        ratingDialog.setTitle(R.string.rating_dialog_title);

        LayoutInflater inflater = this.getLayoutInflater();
        final View ratingBarlayout = inflater.inflate(R.layout.rating_dialog, null);
        ratingDialog.setView(ratingBarlayout);
        ratingDialog.setPositiveButton(R.string.rate_it_dialog_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RatingBar ratingBar = (RatingBar)ratingBarlayout.findViewById(R.id.dialog_ratingbar);
                Log.i("item act", String.valueOf(ratingBar.getRating()));
                item.setRating(ratingBar.getRating());
                AddRateService addRateService= new AddRateService(ItemActivity.this,item, null,true);
                addRateService.execute();
                //Toast.makeText(ItemActivity.this, "Lesa el service mat3amaletsh :D", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = ratingDialog.create();
        alertDialog.show();
    }

    public void openRestaurantHandler(View view){
        GetRestaurantByNameService getRestaurantByNameService = new GetRestaurantByNameService(this,item.getRestaurantName());
        getRestaurantByNameService.execute();
    }

    private void connectionDialog() {
        final Dialog dialog = new Dialog(ItemActivity.this);
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
                ConnectToRestaurantService connectToRestaurantService = new ConnectToRestaurantService(ItemActivity.this,
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
                DisconnectToRestaurantService disconnectToRestaurantService = new DisconnectToRestaurantService(ItemActivity.this, connectionRequest);
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
