package com.example.stocknews;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/*
用户缓存数据库
 */

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper myDBHelper;
    private static final String Column_1 = "uid";
    private static final String Column_2 = "favor";
    public static final String NAME = "stock_news.db";
    public DBHelper(Context context,String name,int version) {
        super(context, name, null, version);
    }

    public  static DBHelper getInstance(Context context,String name,int version) {
        if (myDBHelper == null) {
            myDBHelper = new DBHelper(context, name, version);
        }
        return myDBHelper;
    }

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
    public static void setFAVOR(DBHelper dbHelper,boolean state,int uid, int favor){
        SQLiteDatabase favordb = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
            if (state == true) {
                cv.put(Column_1,uid);
                cv.put(Column_2,favor);
                favordb.insert("User_Favor", null, cv);
                cv.clear();
            }

    }

}
