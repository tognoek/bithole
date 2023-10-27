package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SQLITE extends SQLiteOpenHelper {

    private Context context;
    private static String name = "Account";
    private static Integer version = 1;
    public SQLITE(@Nullable Context context) {
        super(context, name, null, version);
        this.context = context;
    }

    public void QuerySql(String Sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(Sql);
    }

    public Cursor GetDate(String Sql){
        SQLiteDatabase database = getWritableDatabase();
        return database.rawQuery(Sql, null);
    }

    public void insert(Account account){
        SQLiteDatabase Data = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("[user]", account.getUser());
        cv.put("mail", account.getMail());
        cv.put("password", account.getPassword());
        cv.put("[group]", account.getGroup());
        long result = Data.insert("account", null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {

            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean Findaccount(Account account) {
        String query = "SELECT * FROM account WHERE [user] = '" + account.getUser() + "'";
        SQLiteDatabase Data = this.getReadableDatabase();
        Cursor cursor = null;
        if (Data != null){
            cursor = Data.rawQuery(query, null);
        }
        if (cursor != null){
            cursor.moveToNext();
            if (cursor.getString(3).toString().equals(account.getPassword())){
                return true;
            }
        }
        return false;
    }

    public boolean Findauser(Account account) {
        try {
            String query = "SELECT * FROM account WHERE [user] = '" + account.getUser() + "'";
            SQLiteDatabase Data = this.getReadableDatabase();
            Cursor cursor = null;
            if (Data != null){
                cursor = Data.rawQuery(query, null);
            }
            if (cursor != null && cursor.getCount() != 0){
                return true;
            }
            return false;
        }
        catch (Exception e){
            return false;
        }
    }

    public void Remove(){
        SQLiteDatabase Data = this.getWritableDatabase();
        Data.execSQL("DROP TABLE IF EXISTS account");
        Toast.makeText(context, "Remove Table account", Toast.LENGTH_SHORT).show();
    }

    public void Create(){
        SQLiteDatabase Data = this.getWritableDatabase();
        Data.execSQL("CREATE TABLE account (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    [user] VARCHAR(20),\n" +
                "    mail VARCHAR(55),\n" +
                "    password VARCHAR(25),\n" +
                "    [group] INTEGER\n" +
                ");\n");
        Account acc = new Account("admin", "admin@gmail.com", "thayhuy", 101);
        Toast.makeText(context, "Create Table account", Toast.LENGTH_SHORT).show();
        insert(acc);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE account (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    [user] VARCHAR(20),\n" +
                "    mail VARCHAR(55),\n" +
                "    password VARCHAR(25),\n" +
                "    [group] INTEGER\n" +
                ");\n");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS account");
    }
}
