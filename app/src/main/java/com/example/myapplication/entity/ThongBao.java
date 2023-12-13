package com.example.myapplication.entity;

import java.util.Date;

public class ThongBao implements Comparable<ThongBao>{
   private int id;
   private String idSanPham;
   private String idNguoiDang;
   //1: Binh luan || 2: Tuong tac binh luan || 3: Dat hang
   private int loaiTb;
   private String NoiDung;
   private int ngay;
    public ThongBao(int id, String idSanPham, String idNguoiDang, int loaiTb, String noiDung, int ngay) {
        this.id = id;
        this.idSanPham = idSanPham;
        this.idNguoiDang = idNguoiDang;
        this.loaiTb = loaiTb;
        NoiDung = noiDung;
        this.ngay = ngay;
    }
    public ThongBao() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getIdNguoiDang() {
        return idNguoiDang;
    }

    public void setIdNguoiDang(String idNguoiDang) {
        this.idNguoiDang = idNguoiDang;
    }

    public int getLoaiTb() {
        return loaiTb;
    }

    public void setLoaiTb(int loaiTb) {
        this.loaiTb = loaiTb;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public int getNgay() {
        return ngay;
    }

    public void setNgay(int ngay) {
        this.ngay = ngay;
    }

    @Override
    public int compareTo(ThongBao thongBao) {
        return Integer.compare(thongBao.getNgay(), this.getNgay());
    }
}
