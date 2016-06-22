package com.sparta.imenu_client.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.Section;
import com.sparta.imenu_client.userInterface.ItemRecyclerViewAdapter;

import java.util.ArrayList;

public class RestaurantSectionFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Section section;
    private String restaurantName;

    public RestaurantSectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle extras=getArguments();
        section = (Section) extras.get("section");
        restaurantName = (String) extras.get("restaurantName");
        Log.i("section frag", String.valueOf(section.getItems().size()));

        View view= inflater.inflate(R.layout.fragment_restaurant_section,container,false);

        ItemRecyclerViewAdapter adapter = new ItemRecyclerViewAdapter(section.getItems(),getActivity(),restaurantName);
        recyclerView = (RecyclerView) view.findViewById(R.id.restaurant_section_recycler_view);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        Log.i("section frag", "after adapter");

        return view;
    }


}
