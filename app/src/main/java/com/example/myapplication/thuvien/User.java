package com.example.myapplication.thuvien;

public class User {
    private String anh;
    private String ten;
    private String gioitinh;
    private String sdt;

    public User(String anh, String ten, String gioitinh, String sdt) {
        this.anh = anh;
        this.ten = ten;
        this.gioitinh = gioitinh;
        this.sdt = sdt;
    }
    public User() {

    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
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
