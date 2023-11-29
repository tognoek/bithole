package com.example.myapplication.thuvien;

public class FormatVND {
    private String VND;

    public FormatVND(String VND) {
        this.VND = VND;
    }

    public String getVND() {
        String stringIntVND = "";
        for (int i = 0; i < this.VND.length(); i++){
            char c = this.VND.charAt(i);
            if (c != '.'){
                stringIntVND += c;
            }
            else{
                break;
            }
        }
        String returnVND = "";
        int k = 0;
        for (int i = stringIntVND.length() - 1; i > -1; i--){
            if (k == 3){
                k = 0;
                returnVND = "." + returnVND;
            }
            returnVND = stringIntVND.charAt(i) + returnVND;
            k++;
        }
        returnVND = returnVND + " VNĐ";
        return returnVND;
    }

}
