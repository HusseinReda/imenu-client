package com.sparta.imenu_client.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.service.Auxiliary;
import com.sparta.imenu_client.userInterface.OrderRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hussein Abu Maash on 6/14/2016.
 */

public class OrderDialogFragment extends DialogFragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private OrderRecyclerViewAdapter orderRecyclerViewAdapter;
    private FloatingActionButton fab;

    public void setFab(FloatingActionButton fab) {
        this.fab = fab;
    }

    public OrderDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static OrderDialogFragment newInstance(String title,FloatingActionButton fab) {
        OrderDialogFragment frag = new OrderDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        frag.setFab(fab);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_dialog_layout, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        recyclerView = (RecyclerView) view.findViewById(R.id.order_dialog_recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

//        List<String> notifications = new ArrayList<>();
//        notifications.add("chef Table 1 's order is ready");
//        notifications.add("chef Table 2 's order is ready");
//        notifications.add("cust Table 2 is calling");
//        notifications.add("cust Table 3 is calling");
//        notifications.add("chef Table 3 's order is ready");
        //hardcode order items

        orderRecyclerViewAdapter = new OrderRecyclerViewAdapter(Auxiliary.order,view.getContext(),fab);
        if(orderRecyclerViewAdapter.getItemCount()==0)
            Toast.makeText(view.getContext(),"hamada",Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(orderRecyclerViewAdapter);
    }
}
