package com.example.stocknews;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context,String name,int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "create table User_Favor (uid INTEGER NOT NULL,favor int)";
        String createHotNewsTable = "create table Hot_News (hnid INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,ctime DATETIME,title VARCHAR,url VARCHAR,summary TEXT)";
        String createRollNewsTable = "create table Roll_News (rnid INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,ctime DATETIME,title VARCHAR,url VARCHAR,picture_url VARCHAR,summary TEXT)";
        db.execSQL(createUserTable);
        db.execSQL(createHotNewsTable);
        db.execSQL(createRollNewsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
