package com.example.myapplication.thuvien;

public class FormatTime {
    private String time;

    public FormatTime(String time) {
        this.time = time;
    }

    public String getTime(){
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

        return  time[2] + " Tháng " + time[1] + " " + time[0];
    }


}
