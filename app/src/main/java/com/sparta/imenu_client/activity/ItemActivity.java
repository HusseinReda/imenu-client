package com.sparta.imenu_client.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.service.AddRateService;
import com.sparta.imenu_client.service.GetRestaurantByNameService;
import com.sparta.imenu_client.userInterface.LogoutDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Item item;
    TextView ingredientsData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent myIntent = getIntent();
        item = (Item) myIntent.getSerializableExtra("item");

        Toolbar toolbar = (Toolbar) findViewById(R.id.item_toolbar);
        setSupportActionBar(toolbar);

        setUpSearch();



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

    private void setUpSearch() {
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(new ComponentName(this, SearchResults.class)));
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
                profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.getApplicationContext().startActivity(profileIntent);
                return true;

            case R.id.action_logout:
                LogoutDialog logoutDialog = new LogoutDialog(this);
                return true;

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
        itemPrice.setText(Double.toString(item.getPrice()));
        itemRating.setText("Rating = "+ Double.toString(item.getRating()));

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
            slide_up(this, ingredientsData);
            ingredientsData.setVisibility(View.GONE);
        }
        else{
            ingredientsData.setVisibility(View.VISIBLE);
            slide_down(this, ingredientsData);
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
                AddRateService addRateService= new AddRateService(ItemActivity.this,item.getId(),ratingBar.getRating(),true);
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

    //    TODO move to animation class
    public static void slide_down(Context context, View v){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        if(animation != null){
            animation.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(animation);
            }
        }
    }

    //    TODO move to animation class
    public static void slide_up(Context context, View v){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        if(animation != null){
            animation.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(animation);
            }
        }
    }
}
