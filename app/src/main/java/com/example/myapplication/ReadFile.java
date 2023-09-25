package com.example.myapplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {
    public static ArrayList<Account> Read() {
        ArrayList<Account> list = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader("file.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String[] acc;

            while ((line = bufferedReader.readLine()) != null) {
                acc = line.split(", ");
                list.add(new Account(acc[0], acc[1], acc[2]));
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static String Find(Account use){
        ArrayList<Account> list = new ArrayList<>();
        list = Read();
        for (Account a : list){
            if (a.getUser().equals(use.getUser()) && a.getPassword().equals(use.getPassword())){
                return "YES";
            }
        }
        return "NO";
    }

    public static String FindUser(Account use){
        ArrayList<Account> list = new ArrayList<>();
        list = Read();
        for (Account a : list){
            if (a.getUser().equals(use.getUser())){
                return "YES";
            }
        }
        return "NO";
    }
}
