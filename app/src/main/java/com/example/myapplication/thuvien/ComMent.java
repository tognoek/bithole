package com.example.myapplication.thuvien;

public class ComMent {
    private String comment;
    private String name;

    public ComMent(String name, String comment) {
        this.comment = comment;
        this.name = name;
    }
    public ComMent() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String iduser) {
        this.name = iduser;
    }
}
