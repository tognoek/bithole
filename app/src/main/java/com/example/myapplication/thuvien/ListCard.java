package com.example.myapplication.thuvien;

public class ListCard {
    private int id;
    private int soluong;

    public ListCard(int id, int soluong) {
        this.id = id;
        this.soluong = soluong;
    }

    public ListCard() {
    }

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
}
