package com.example.myapplication.entity;

import java.io.Serializable;

public class Cart implements Serializable {
    private int id;
    private String name;
    private String mota;
    private double dongia;
    private int soluong;
    private String hinhanh;

    private  String shop;
    private String idShop;

    public Cart() {
    }

    public Cart(int id, String name, String mota, double dongia, int soluong, String hinhanh, String shop, String idShop) {
        this.id = id;
        this.name = name;
        this.mota = mota;
        this.dongia = dongia;
        this.soluong = soluong;
        this.hinhanh = hinhanh;
        this.shop = shop;
        this.idShop = idShop;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
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

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public double getDongia() {
        return dongia;
    }

    public void setDongia(double dongia) {
        this.dongia = dongia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getIdShop() {
        return idShop;
    }

    public void setIdShop(String idShop) {
        this.idShop = idShop;
    }
}
