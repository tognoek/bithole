package com.example.myapplication.entity;

import java.util.Date;

public class ThongBao {
   private int id;
   private String idSanPham;
   private String idNguoiDang;
   private String NoiDung;
   private int ngayBL;
    public ThongBao(int id, String idSanPham, String idNguoiDang, String noiDung, int ngayBL) {
        this.id = id;
        this.idSanPham = idSanPham;
        this.idNguoiDang = idNguoiDang;
        NoiDung = noiDung;
        this.ngayBL = ngayBL;
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

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public int getNgayBL() {
        return ngayBL;
    }

    public void setNgayBL(int ngayBL) {
        this.ngayBL = ngayBL;
    }
}
