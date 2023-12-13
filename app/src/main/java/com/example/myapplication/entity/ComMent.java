package com.example.myapplication.entity;

public class ComMent {
    private String comment;
    private String name;
    private String id;
    private int stt;
    private String time;
    public ComMent(String name, String comment, String id, int stt, String time) {
        this.comment = comment;
        this.name = name;
        this.id  = id;
        this.stt = stt;
        this.time = time;
    }
    public ComMent() {
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
