package com.sparta.imenu_client.model;

import java.util.List;

/**
 * Created by Hussein Abu Maash on 3/10/2016.
 */

public class UserSpec {
    private int id;
    private int userId;
    private String name;
    private String note;
    private List<String> keywords;

    public UserSpec(int userId, String name, String note, List<String> keywords) {
        this.userId = userId;
        this.name = name;
        this.note = note;
        this.keywords = keywords;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
