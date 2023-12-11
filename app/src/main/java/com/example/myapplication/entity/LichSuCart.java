package com.example.myapplication.entity;

public class LichSuCart {
    private int id;
    private int idSanPham;
    private int soluong;
    private int date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public LichSuCart() {
    }

    public LichSuCart(int id, int idSanPham, int soluong, int date) {
        this.id = id;
        this.idSanPham = idSanPham;
        this.soluong = soluong;
        this.date = date;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }
    public void addIdSanPham(int t) {
        this.idSanPham = this.idSanPham + t;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
