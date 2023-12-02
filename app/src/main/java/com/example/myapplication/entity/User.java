package com.example.myapplication.entity;

public class User {
    private String id;
    private String ten;
    private String gioitinh;
    private String sdt;

    public User(String id, String ten, String gioitinh, String sdt) {
        this.id = id;
        this.ten = ten;
        this.gioitinh = gioitinh;
        this.sdt = sdt;
    }
    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String anh) {
        this.id = anh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
