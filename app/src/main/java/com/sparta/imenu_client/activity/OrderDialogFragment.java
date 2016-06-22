package com.sparta.imenu_client.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.service.AddOrderService;
import com.sparta.imenu_client.service.Auxiliary;
import com.sparta.imenu_client.userInterface.OrderRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hussein Abu Maash on 6/14/2016.
 */

public class OrderDialogFragment extends DialogFragment {
    private RecyclerView recyclerView;
    private Button placeOrderButton;
    private LinearLayoutManager linearLayoutManager;
    private OrderRecyclerViewAdapter orderRecyclerViewAdapter;

    public OrderDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static OrderDialogFragment newInstance(String title) {
        OrderDialogFragment frag = new OrderDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        placeOrderButton = (Button) view.findViewById(R.id.place_order_button);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to place this order ?");
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        AddOrderService addOrderService = new AddOrderService(getContext(),Auxiliary.order);
                        addOrderService.execute();
                        dismiss();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog placeOrderDialog = builder.create();
                placeOrderDialog.show();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.order_dialog_recycler_view);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        orderRecyclerViewAdapter = new OrderRecyclerViewAdapter(Auxiliary.order,view.getContext().getApplicationContext());
        if(orderRecyclerViewAdapter.getItemCount()==0)
            Toast.makeText(view.getContext(),"No items added yet",Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(orderRecyclerViewAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

}
