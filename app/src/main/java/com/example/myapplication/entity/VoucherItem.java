package com.example.myapplication.entity;

public class VoucherItem {
    private int id;
    private String name;
    private String mota;
    private int giaGiam;
    private int loai;
    private int maxGia;
    private int minGia;
    private String idShop;

    public int getGiaGiam() {
        return giaGiam;
    }

    public void setGiaGiam(int giaGiam) {
        this.giaGiam = giaGiam;
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

    public void setIdShop(String idShop) {
        this.idShop = idShop;
    }

    public String getIdShop() {
        return idShop;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }

    public int getMaxGia() {
        return maxGia;
    }

    public void setMaxGia(int maxGia) {
        this.maxGia = maxGia;
    }

    public int getMinGia() {
        return minGia;
    }

    public void setMinGia(int minGia) {
        this.minGia = minGia;
    }

    public VoucherItem() {
    }

    public VoucherItem(int id, String name, String mota, int giaGiam, int loai, int maxGia, int minGia, String idShop) {
        this.id = id;
        this.name = name;
        this.mota = mota;
        this.giaGiam = giaGiam;
        this.loai = loai;
        this.maxGia = maxGia;
        this.minGia = minGia;
        this.idShop = idShop;
    }
}
