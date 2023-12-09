package com.example.myapplication.thuvien;

public class GetNameEmail {
    private String name;

    public GetNameEmail(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealNameEmail() {
        String outString = "";
        for (int i = 0; i < this.name.length(); i++){
            if (this.name.charAt(i) == '@'){
                return outString;
            }
            else{
                outString = outString + this.name.charAt(i);
            }
        }


        return outString;
    }
}
