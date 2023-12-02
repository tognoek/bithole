package com.example.myapplication.nome;

import java.io.Serializable;

public class meber  implements Serializable {
    private String name;
    private String masinhvien;
    private int image;

    private String[] listView;

    private String [] listName;

    public meber(String name, String masinhvien, int image, String[] listView, String [] listName) {
        this.name = name;
        this.masinhvien = masinhvien;
        this.image = image;
        this.listView = listView;
        this.listName = listName;
    }

    public String[] getListName() {
        return listName;
    }

    public void setListName(String[] listName) {
        this.listName = listName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMasinhvien() {
        return masinhvien;
    }

    public void setMasinhvien(String masinhvien) {
        this.masinhvien = masinhvien;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String[] getListView() {
        return listView;
    }

    public void setListView(String[] listView) {
        this.listView = listView;
    }
}
