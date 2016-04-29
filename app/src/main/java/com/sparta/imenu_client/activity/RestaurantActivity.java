package com.sparta.imenu_client.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.Contact;
import com.sparta.imenu_client.model.Restaurant;
import com.sparta.imenu_client.userInterface.LogoutDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Restaurant restaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Intent myIntent = getIntent();
        restaurant = (Restaurant) myIntent.getSerializableExtra("restaurant");
        Log.i("rest act",restaurant.getName());

        Toolbar toolbar = (Toolbar) findViewById(R.id.restaurant_toolbar);
        setSupportActionBar(toolbar);

        setUpSearch();

        final Button btn1 = (Button) findViewById(R.id.add_restaurant_rating_button);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showRatingDialog();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.restaurant_sections_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.restaurant_sections);
        tabLayout.setupWithViewPager(viewPager);

        bindData(restaurant);

    }

    private void setUpSearch() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        com.sparta.imenu_client.model.Menu menu = restaurant.getMenu();
        Log.i("rest act", String.valueOf(restaurant.getMenu().getSections().size()));
        for(int i=0;i<menu.getSections().size();i++) {
//            if(menu.getSections().get(i).getItems().size()==0)
//                continue;
            Bundle bundle = new Bundle();
            bundle.putSerializable("section", menu.getSections().get(i));
            RestaurantSectionFragment restaurantSectionFragment = new RestaurantSectionFragment();
            restaurantSectionFragment.setArguments(bundle);
            adapter.addFragment(restaurantSectionFragment,menu.getSections().get(i).getName());
        }
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

    private void bindData(Restaurant restaurant) {
        ImageView restaurantImage = (ImageView) findViewById(R.id.restaurant_image);
        TextView restaurantName = (TextView) findViewById(R.id.restaurant_name);
        TextView restaurantCategory = (TextView) findViewById(R.id.restaurant_category);
        TextView restaurantDescription = (TextView) findViewById(R.id.restaurant_description);
        TextView restaurantContacts = (TextView) findViewById(R.id.restaurant_contacts);
        TextView restaurantRating = (TextView) findViewById(R.id.restaurant_rating);


        Picasso.with(this).load(restaurant.getPicture())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.loading_image)
                .into(restaurantImage);
        restaurantName.setText(restaurant.getName());
        restaurantDescription.setText(restaurant.getDescription());
        restaurantCategory.setText(restaurant.getCategory());
        restaurantRating.setText("Rating = "+ Double.toString(restaurant.getRating()));

        List<String> contacts = restaurant.getContacts();
    }

    public void contactsHandler(View view){
        AlertDialog.Builder contactsDialog = new AlertDialog.Builder(this);
        contactsDialog.setTitle(R.string.restaurant_contacts_dialog_title);

        String contactsString="";
        List<Contact> contacts=restaurant.getContacts();

        for(int i=0;i<contacts.size();i++) {
            contactsString += "* " + contacts.get(i).getAddress() + "\n";
            contactsString += "  " + contacts.get(i).getPhoneNumber();
            if(i<contacts.size()-1)
                contactsString+="\n";
        }
        contactsDialog.setMessage(contactsString);
    }

    public void showRatingDialog() {
        AlertDialog.Builder ratingDialog = new AlertDialog.Builder(this);

        ratingDialog.setCancelable(true);
        ratingDialog.setTitle(R.string.rating_dialog_title);

        LayoutInflater inflater = this.getLayoutInflater();
        View ratingBar = inflater.inflate(R.layout.rating_dialog,null);

        ratingDialog.setView(ratingBar);
        ratingDialog.setPositiveButton(R.string.rate_it_dialog_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //AddRateService addRateService= new AddRateService(ItemActivity.this,,rating.getRating(),false);
                Toast.makeText(RestaurantActivity.this, "Lesa el service mat3amaletsh :D", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = ratingDialog.create();
        alertDialog.show();
    }
}
