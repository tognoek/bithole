package com.example.myapplication.thuvien;

public class ComMent {
    private String comment;
    private String iduser;

    public ComMent(String comment, String iduser) {
        this.comment = comment;
        this.iduser = iduser;
    }
    public ComMent() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }
}
