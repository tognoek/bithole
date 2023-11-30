package com.example.myapplication.thuvien;

public class ThongBao {
    private String tieude;
    private String hinhanh;
    private String mota;

    public ThongBao(String tieude, String hinhanh, String mota) {
        this.tieude = tieude;
        this.hinhanh = hinhanh;
        this.mota = mota;
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
}
