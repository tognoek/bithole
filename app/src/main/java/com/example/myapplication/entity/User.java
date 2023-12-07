package com.example.myapplication.entity;

public class User {
    private String id;
    private String ten;
    private boolean gioitinh;
    private String sdt;
    private int ngaysinh;

    public User(String id, String ten, boolean gioitinh, String sdt, int ngaysinh) {
        this.id = id;
        this.ten = ten;
        this.gioitinh = gioitinh;
        this.sdt = sdt;
        this.ngaysinh = ngaysinh;
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

    public boolean getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(int ngaysinh) {
        this.ngaysinh = ngaysinh;
    }
}
