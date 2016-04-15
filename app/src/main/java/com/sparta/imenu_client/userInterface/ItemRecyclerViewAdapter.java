package com.sparta.imenu_client.userInterface;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
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

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder> {


    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView itemImage;
        TextView itemName;
        TextView itemDescription;
        TextView itemPrice;
        TextView itemRestaurantName;


        ItemViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.card_view);
            itemImage =(ImageView)itemView.findViewById(R.id.item_image);
            itemName =(TextView)itemView.findViewById(R.id.item_name);
            itemDescription =(TextView)itemView.findViewById(R.id.item_description);
            itemPrice =(TextView)itemView.findViewById(R.id.item_price);
            itemRestaurantName =(TextView)itemView.findViewById(R.id.item_restaurant_name);
        }
    }

    List<Item> items;
    Context context;

    public ItemRecyclerViewAdapter(List<Item> items, Context context) {
        this.items = items;
        this.context=context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        ItemViewHolder itemViewHolder= new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.itemName.setText(items.get(i).getName());
        itemViewHolder.itemDescription.setText(items.get(i).getDescription());
        itemViewHolder.itemPrice.setText(Double.toString(items.get(i).getPrice()));
        itemViewHolder.itemRestaurantName.setText(items.get(i).getRestaurantName());
//        itemViewHolder.itemImage.setImageURI(Uri.parse(items.get(i).getPicture()));
        Picasso.with(context).load(items.get(i).getPicture()).into(itemViewHolder.itemImage);
//        itemViewHolder.itemImage.setImageResource(R.drawable.no_image);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
