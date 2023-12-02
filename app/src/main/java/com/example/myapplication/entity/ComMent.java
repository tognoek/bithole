package com.example.myapplication.entity;

public class ComMent {
    private String comment;
    private String name;
    private String id;

    private String time;
    public ComMent(String name, String comment, String id, String time) {
        this.comment = comment;
        this.name = name;
        this.id  = id;
        this.time = time;
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
