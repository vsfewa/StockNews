package com.example.stocknews;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;




public class ChooseFavorActivity extends Activity implements View.OnClickListener{

    private final int FAVOR_1 = 1;
    private final int FAVOR_2 = 2;
    private final int FAVOR_3 = 3;
    private final int DEFAULT_NULL = 0;
    private final int TEST_UID = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_favor);
        DBHelper db = new DBHelper(ChooseFavorActivity.this,"stock_news.db",1);
        SQLiteDatabase favordb = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        /*
        cv.put("uid",DEFAULT_NULL);
        cv.put("favor", DEFAULT_NULL);
        */
        favordb.insert("User_Favor", null, cv);

    }

    public void onClick(View v) {
        DBHelper db = new DBHelper(ChooseFavorActivity.this,"stock_news.db",1);
        SQLiteDatabase favordb = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        switch (v.getId())
        {
            case R.id.button_1:
                cv.put("uid",TEST_UID);
                cv.put("favor",FAVOR_1);
                favordb.insert("User_Favor", null, cv);
                cv.clear();
                break;
            case R.id.button_2:
                cv.put("uid",TEST_UID);
                cv.put("favor",FAVOR_2);
                favordb.insert("User_Favor", null, cv);
                cv.clear();
                break;
            case R.id.button_3:
                cv.put("uid",TEST_UID);
                cv.put("favor",FAVOR_3);
                favordb.insert("User_Favor", null, cv);
                cv.clear();
                break;
            case R.id.button_skip:
                JumptoNews();
                break;
            case R.id.button_start:
                JumptoNews();
                break;
        }
    }

    public void JumptoNews(){
        Intent intent = new Intent(ChooseFavorActivity.this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}