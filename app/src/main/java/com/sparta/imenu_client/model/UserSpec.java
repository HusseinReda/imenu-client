package com.sparta.imenu_client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hussein Abu Maash on 3/10/2016.
 */

public class UserSpec implements Serializable{
    private int id;
    private String name;
    private String note;
    private boolean type; // True for health issue, False for restriction
    private ArrayList<String> keywords;

    public UserSpec() {
    }

    public UserSpec(String name, String note, boolean type, ArrayList<String> keywords) {
        this.name = name;
        this.note = note;
        this.type = type;
        this.keywords = keywords;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
