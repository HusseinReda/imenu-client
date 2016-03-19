package com.sparta.imenu_client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hamed on 3/19/16.
 */
public class Section {
    private int id;
    private List<Item> items;
    private int menuId;
    public int getId(){
        return id;
    }
    public int getMenuId() {
        return menuId;
    }

    public List<Item> getItems() {
        return items;
    }

    public Section(List<Item> items, int menuId) {

        this.items = items;
        this.menuId = menuId;
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
}
