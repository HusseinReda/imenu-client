package com.sparta.imenu_client.model;

/**
 * Created by Hussein Abu Maash on 5/20/2016.
 */

public class OrderCard {
    private Long id;
    Item item;
    int count;
    /*
    There are 5 states
    ------------------
    1 -> not ordered
    2 -> ordered
    3 -> in_due
    4 -> done
     */
    int state;

    Order order;

    public OrderCard() {
    }

    public OrderCard(Item item, int count, int state) {
        this.item = item;
        this.count = count;
        this.state = state;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
