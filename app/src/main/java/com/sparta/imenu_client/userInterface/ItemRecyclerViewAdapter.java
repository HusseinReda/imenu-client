package com.sparta.imenu_client.userInterface;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.ItemActivity;
import com.sparta.imenu_client.model.Item;
import com.sparta.imenu_client.model.OrderCard;
import com.sparta.imenu_client.service.Auxiliary;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Hussein Abu Maash on 4/10/2016.
 */

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder> {

    List<Item> items;
    static Context context;
    static String restaurantName;

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView itemImage;
        TextView itemName;
        TextView itemDescription;
        TextView itemPrice;
        TextView itemRestaurantName;
        TextView itemId;
        public Item currentItem;
        Button addToOrder;

        ItemViewHolder(final View itemView) {
            super(itemView);

            cardView=(CardView)itemView.findViewById(R.id.card_view);
            itemImage =(ImageView)itemView.findViewById(R.id.cardview_item_image);
            itemName =(TextView)itemView.findViewById(R.id.cardview_item_name);
            itemDescription =(TextView)itemView.findViewById(R.id.cardview_item_description);
            itemPrice =(TextView)itemView.findViewById(R.id.cardview_item_price);
            itemRestaurantName =(TextView)itemView.findViewById(R.id.cardview_item_restaurant_name);
            itemId = (TextView)itemView.findViewById(R.id.cardview_item_id);
            addToOrder = (Button) itemView.findViewById(R.id.add_item_to_order);

            if(Auxiliary.connectedToRest!=null && Auxiliary.connectedToRest.equals(restaurantName))
                addToOrder.setVisibility(View.VISIBLE);
            else
                addToOrder.setVisibility(View.GONE);

            addToOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QuantityPickerDialog quantityPickerDialog = new QuantityPickerDialog(context,currentItem,false);
                    quantityPickerDialog.show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent itemActivity = new Intent(itemView.getContext(), ItemActivity.class);
                    itemActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.i("item adptr", "id=" + currentItem.getId());
                    itemActivity.putExtra("item", currentItem);
                    itemView.getContext().startActivity(itemActivity);
                }
            });
        }
    }

    public ItemRecyclerViewAdapter(List<Item> items, Context context,String restaurantName) {
        this.items = items;
        this.context=context;
        this.restaurantName=restaurantName;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_layout,parent,false);
        ItemViewHolder itemViewHolder= new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {

        Item currentItem=items.get(i);
        itemViewHolder.currentItem=currentItem;

        if(Auxiliary.connectedToRest!=null && Auxiliary.connectedToRest.equals(restaurantName))
            itemViewHolder.addToOrder.setVisibility(View.VISIBLE);
        else
            itemViewHolder.addToOrder.setVisibility(View.GONE);

        itemViewHolder.itemId.setText(Long.toString(items.get(i).getId()));
        itemViewHolder.itemName.setText(items.get(i).getName());
        itemViewHolder.itemDescription.setText(items.get(i).getDescription());
        itemViewHolder.itemPrice.setText(Double.toString(items.get(i).getPrice())+" LE");
        itemViewHolder.itemRestaurantName.setText(items.get(i).getRestaurantName());
        Picasso.with(context).load(items.get(i).getPicture())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.loading_image)
                .into(itemViewHolder.itemImage);

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

}
