package com.example.myapplication.entity;

public class ThongBao {
    private String tieude;
    private String hinhanh;
    private String mota;
    private String loaithongbao;

    public ThongBao(String tieude, String hinhanh, String mota, String loaithongbao) {
        this.tieude = tieude;
        this.hinhanh = hinhanh;
        this.mota = mota;
        this.loaithongbao = loaithongbao;
    }
    public ThongBao() {
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getLoaithongbao() {
        return loaithongbao;
    }

    public void setLoaithongbao(String loaithongbao) {
        this.loaithongbao = loaithongbao;
    }
}
