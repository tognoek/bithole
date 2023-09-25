package com.example.myapplication;
public class Account {
    private String user;
    private String mail;
    private String password;

    public Account(){

    }
    public  Account(String user, String mail, String password){
        this.user = user;
        this.mail = mail;
        this.password = password;
    }

    public  Account(String user, String password){
        this.user = user;
        this.mail = "";
        this.password = password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUser() {
        return user;
    }

    public String getMail() {
        return mail;
    }

}
