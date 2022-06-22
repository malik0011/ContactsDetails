package com.example.khabar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {
    public dbHelper(Context context) {
        super(context, "ContactDetails.db", null, 1);//db name and the version
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        //we have toi create a qry
        DB.execSQL("create Table UserDetails(name TEXT primary key,email TEXT,gender TEXT,phone TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("Drop Table if exists UserDetails");
    }

    public Boolean insertUserData(String Name,String Email,String Gender,String Phone){

        SQLiteDatabase DB  = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",Name);
        contentValues.put("email",Email);
        contentValues.put("gender",Gender);
        contentValues.put("phone",Phone);

        long result = DB.insert("UserDetails",null,contentValues);
        if(result==-1) return false;
        else return true;

    }

    public Cursor showData(){
        SQLiteDatabase DB  = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from UserDetails",null);
        return cursor;
    }
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ "UserDetails");
        db.close();
    }
}
