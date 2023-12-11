package com.example.myapplication.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SanPhamLichSu implements Serializable {
    private int id;
    private String name;
    private String mota;
    private double dongia;
    private int soluong;
    private String hinhanh;
    private String idshop;
    private int date;

    public SanPhamLichSu() {
    }

    public SanPhamLichSu(int id, String name, String mota, double dongia, int soluong, String hinhanh, String idshop, int date) {
        this.id = id;
        this.name = name;
        this.mota = mota;
        this.dongia = dongia;
        this.soluong = soluong;
        this.hinhanh = hinhanh;
        this.idshop = idshop;
        this.date = date;
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

    public String getIdshop() {
        return idshop;
    }

    public void setIdshop(String idshop) {
        this.idshop = idshop;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

}
