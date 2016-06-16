package com.sparta.imenu_client.userInterface;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.Order;
import com.sparta.imenu_client.model.OrderCard;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Hussein Abu Maash on 6/14/2016.
 */

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.ItemViewHolder>{
    Order order;
    List<OrderCard> orderCardList;
    Context context;
    FloatingActionButton fab;

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        CardView cardView;


        public ItemViewHolder(final View itemView) {
            super(itemView);
//            cardView = (CardView) itemView.findViewById(R.id.notification_card_view);
//            notificationText = (TextView) itemView.findViewById(R.id.notification_card_view_text);
//            notificationImage = (ImageView) itemView.findViewById(R.id.notification_card_view_image);
            cardView.setLongClickable(true);
            cardView.setOnLongClickListener(this);
        }



        @Override
        public boolean onLongClick(View v) {
//            order.remove(getAdapterPosition());
//            notifyItemRemoved(getAdapterPosition());
            return true;
        }
    }



    public OrderRecyclerViewAdapter(Order order, Context context, FloatingActionButton fab) {
        this.order = order;
        orderCardList=order.getOrderCardList();
        this.context = context;
        this.fab = fab;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card_layout,parent,false);
        ItemViewHolder itemViewHolder= new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {

//        String currentNotification = order.get(i);
//
//        itemViewHolder.notificationText.setText(currentNotification.substring(5));
//        if(currentNotification.substring(0,4).equals("chef"))
//            Picasso.with(context).load(R.drawable.food_icon).into(itemViewHolder.notificationImage);
//        else
//            Picasso.with(context).load(R.drawable.call_waiter_icon).into(itemViewHolder.notificationImage);

        //fab.setBackgroundTintList(ColorStateList.valueOf(0xffff0000));
    }

    @Override
    public int getItemCount() {
        return orderCardList.size();
    }

}
