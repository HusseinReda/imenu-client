package com.sparta.imenu_client.userInterface;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.model.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Hussein Abu Maash on 4/10/2016.
 */

public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.RestaurantViewHolder> {

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView restaurantImage;
        TextView restaurantName;
        TextView restaurantDescription;
        TextView restaurantCategory;

        RestaurantViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.card_view);
            restaurantImage =(ImageView)itemView.findViewById(R.id.restaurant_image);
            restaurantName =(TextView)itemView.findViewById(R.id.restaurant_name);
            restaurantDescription =(TextView)itemView.findViewById(R.id.restaurant_description);
            restaurantCategory =(TextView)itemView.findViewById(R.id.restaurant_category);
        }
    }

    List<Restaurant> restaurants;
    Context context;

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
        restaurantViewHolder.restaurantName.setText(restaurants.get(i).getName());
        restaurantViewHolder.restaurantDescription.setText(restaurants.get(i).getDescription());
        restaurantViewHolder.restaurantCategory.setText(restaurants.get(i).getCategory());
//        restaurantViewHolder.restaurantImage.setImageURI(Uri.parse("https://www.ginesisnatural.com/images/no_image.jpg"));
//        restaurantViewHolder.restaurantImage.setImageResource(R.drawable.no_image);
        Picasso.with(context).load(restaurants.get(i).getPicture()).into(restaurantViewHolder.restaurantImage);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }
}