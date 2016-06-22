package com.sparta.imenu_client.userInterface;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.Order;
import com.sparta.imenu_client.model.OrderCard;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hussein Abu Maash on 6/14/2016.
 */

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.ItemViewHolder>{
    Order order;
    List<OrderCard> orderCardList;
    Context context;

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView orderItemImage;
        TextView orderItemName;
        Button orderItemQuantity;
        ImageButton removeOrderItemButton;
        AppCompatSpinner quantitySpinner;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.order_item_card_view);
            orderItemImage = (ImageView) itemView.findViewById(R.id.order_item_card_view_image);
            orderItemName= (TextView) itemView.findViewById(R.id.order_item_card_view_name);
            quantitySpinner = (AppCompatSpinner) itemView.findViewById(R.id.order_item_card_view_spinner);
            ArrayList<Integer> list = new ArrayList<Integer>();
            for(int i=1;i<=50;i++)
                list.add(i);
            ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(context,android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            quantitySpinner.setAdapter(dataAdapter);
            removeOrderItemButton= (ImageButton) itemView.findViewById(R.id.remove_order_item);
            removeOrderItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderCardList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
        }
    }



    public OrderRecyclerViewAdapter(Order order, Context context) {
        this.order = order;
        orderCardList=order.getOrderCardList();
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card_layout,parent,false);
        ItemViewHolder itemViewHolder= new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        OrderCard orderCard = orderCardList.get(i);
        orderCardList.get(i).setCount((Integer) itemViewHolder.quantitySpinner.getSelectedItem());
        itemViewHolder.orderItemName.setText(orderCard.getItem().getName());
        itemViewHolder.quantitySpinner.setSelection(orderCardList.get(i).getCount() - 1);
        Picasso.with(context).load(orderCard.getItem().getPicture())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.loading_image)
                .into(itemViewHolder.orderItemImage);
    }

    @Override
    public int getItemCount() {
        return orderCardList.size();
    }

}
