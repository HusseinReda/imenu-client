package com.sparta.imenu_client.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hamed on 3/19/16.
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class Section implements Serializable{
    private int id;
    private List<Item> items;
    private String name;
    public int getId(){
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public Section() {
    }

    public Section(List<Item> items) {
        this.items = items;
    }

    public Section(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }

    public void addItem (Item item){
        items.add(item);
        // call the service
    }
    public void removeItem (int itemId){
        List<Item> temp=  new ArrayList<Item>();
        for (int i=0;i<items.size();i++){
            if (items.get(i).getId()!=itemId){
                items.add(items.get(i)) ;
            }
        }
        items= temp;
        // call the service
    }

    public String getName() {
        return name;
    }
}
