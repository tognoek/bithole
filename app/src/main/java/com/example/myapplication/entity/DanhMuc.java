package com.example.myapplication.entity;

public class DanhMuc {
    private int id;
    private String name;

    private DanhMuc(){

    }

    public DanhMuc(int id, String name) {
        this.id = id;
        this.name = name;
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
}
