package com.example.myapplication.entity;

import java.io.Serializable;

public class Shop implements Serializable {
    private String id;

    public Shop(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
