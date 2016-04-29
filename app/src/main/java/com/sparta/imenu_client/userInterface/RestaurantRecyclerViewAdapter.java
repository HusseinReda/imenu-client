package com.sparta.imenu_client.userInterface;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.RestaurantActivity;
import com.sparta.imenu_client.model.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Hussein Abu Maash on 4/10/2016.
 */

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.RestaurantViewHolder> {

    List<Restaurant> restaurants;
    Context context;

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView restaurantImage;
        TextView restaurantName;
        TextView restaurantDescription;
        TextView restaurantCategory;
        public Restaurant currentRestaurant;

        RestaurantViewHolder(final View itemView) {
            super(itemView);

            cardView=(CardView)itemView.findViewById(R.id.card_view);
            restaurantImage =(ImageView)itemView.findViewById(R.id.cardview_restaurant_image);
            restaurantName =(TextView)itemView.findViewById(R.id.cardview_restaurant_name);
            restaurantDescription =(TextView)itemView.findViewById(R.id.cardview_restaurant_description);
            restaurantCategory =(TextView)itemView.findViewById(R.id.cardview_restaurant_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent restaurantActivity = new Intent(itemView.getContext(), RestaurantActivity.class);
                    restaurantActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.i("rest adptr", "name=" + currentRestaurant.getName());
                    restaurantActivity.putExtra("restaurant", currentRestaurant);
                    itemView.getContext().startActivity(restaurantActivity);
                }
            });
        }
    }

    public RestaurantRecyclerViewAdapter(List<Restaurant> items,Context context) {
        this.restaurants = items;
        this.context=context;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_card_layout,parent,false);
        RestaurantViewHolder restaurantViewHolder= new RestaurantViewHolder(view);
        return restaurantViewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder restaurantViewHolder, int i) {

        Restaurant curRestaurant = restaurants.get(i);
        restaurantViewHolder.currentRestaurant=curRestaurant;

        restaurantViewHolder.restaurantName.setText(restaurants.get(i).getName());
        restaurantViewHolder.restaurantDescription.setText(restaurants.get(i).getDescription());
        restaurantViewHolder.restaurantCategory.setText(restaurants.get(i).getCategory());
//        restaurantViewHolder.restaurantImage.setImageURI(Uri.parse("https://www.ginesisnatural.com/images/no_image.jpg"));
//        restaurantViewHolder.restaurantImage.setImageResource(R.drawable.no_image);
        Picasso.with(context).load(restaurants.get(i).getPicture())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.loading_image)
                .into(restaurantViewHolder.restaurantImage);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }
}