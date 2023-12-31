package com.example.myapplication.thuvien;

import java.text.Format;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class FormatTime {
    private String time;

    public FormatTime(String time) {
        this.time = time;
    }

    public String getTime(){
        String[] time = new String[3];
        int k = 0;
        time[k] = "";
        for (int i = 0; i < this.time.length(); i++){
            if (this.time.charAt(i) == ' '){
                break;
            }
            if (this.time.charAt(i) == '-'){
                k++;
                time[k] = "";
            }else{
                time[k] = time[k] + this.time.charAt(i);
            }
        }

        return  time[2] + " Tháng " + time[1] + " " + time[0];
    }
    public String getTimeTwo(){
        String[] time = new String[3];
        time[0] = "" + this.time.charAt(0) + this.time.charAt(1) + this.time.charAt(2) + this.time.charAt(3);
        time[1] = "" + this.time.charAt(4) + this.time.charAt(5);
        time[2] = "" + this.time.charAt(6) + this.time.charAt(7);
        return  time[2] + " Tháng " + time[1] + " " + time[0];
    }

    public int getTimeInteger(){
        String returnStringTime = "";
        String[] time = new String[3];
        int k = 0;
        time[k] = "";
        for (int i = 0; i < this.time.length(); i++){
            if (this.time.charAt(i) == ' '){
                break;
            }
            if (this.time.charAt(i) == '-'){
                k++;
                time[k] = "";
            }else{
                time[k] = time[k] + this.time.charAt(i);
            }
        }
        returnStringTime = time[0] + time[1] + time[2];
        return  Integer.parseInt(returnStringTime);
    }

}
