package com.example.myapplication.entity;

public class Gacha {
    private int id;
    private int so;

    public Gacha() {
    }

    public Gacha(int id, int so) {
        this.id = id;
        this.so = so;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSo() {
        return so;
    }

    public void setSo(int so) {
        this.so = so;
    }
}
