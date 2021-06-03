package com.example.stocknews;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class ChooseFavorActivity extends Activity implements View.OnClickListener{

    private final int [] FAVOR = {1,2,3,4,5,6};
    private final int TEST_UID = 1;
    /*
    按钮总数，每行按钮数
     */
    private final int BUTTON_NUM = 6;
    private final int BUTTON_IN_ONE_LINE = 3;
    /*按钮大小

     */
    private final int BUTTON_WIDTH = 180;
    private final int BUTTON_HEIGHT = 150;
    private Button[] button = new Button[BUTTON_NUM];
    private boolean[] selected = new boolean[BUTTON_NUM];
    private List<String> FAVOR_TYPE = new ArrayList<>();
    private  ContentValues cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_favor);
        FAVOR_TYPE.add(this.getString(R.string.favor1));
        FAVOR_TYPE.add(this.getString(R.string.favor2));
        FAVOR_TYPE.add(this.getString(R.string.favor3));
        FAVOR_TYPE.add(this.getString(R.string.favor4));
        FAVOR_TYPE.add(this.getString(R.string.favor5));
        FAVOR_TYPE.add(this.getString(R.string.favor6));
        RelativeLayout relativeLayout = findViewById(R.id.button_view);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int row = 0;
        /*
        动态加载button
         */
        for (int i = 0; i < BUTTON_NUM; i++) {
            button[i] = new Button(this);
            button[i].setId(i);
            button[i].setText(FAVOR_TYPE.get(i));
            button[i].setBackgroundResource(R.drawable.button_bg);
            button[i].setGravity(Gravity.CENTER);
            button[i].setOnClickListener(this);
            RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(BUTTON_WIDTH, BUTTON_HEIGHT);
            if(i % BUTTON_IN_ONE_LINE == 0) row++;
            btParams.leftMargin = width/8 + (BUTTON_WIDTH + width/10) * (i % BUTTON_IN_ONE_LINE);
            btParams.topMargin =  height/4 +  (BUTTON_HEIGHT + width/10) * row;
            relativeLayout.addView(button[i],btParams);
        }
    }
    /*
    按钮监听事件，设置选中状态，保存用户偏好
     */
    public void onClick(View v) {
        for (int i = 0; i < button.length; i++) {
            if (v.getId() == button[i].getId()) {
                if (selected[i] != true) {
                    button[i].setBackgroundResource(R.drawable.button_click);
                    selected[i] = true;
                }
                else {
                    button[i].setBackgroundResource(R.drawable.button_bg);
                    selected[i] = false;
                }
            }
        }
        switch (v.getId())
        {
            case R.id.button_skip:
                JumptoNews();
                break;
            case R.id.button_start:
                setFAVOR();
                JumptoNews();
                break;
        }
    }

    public void JumptoNews(){
        Intent intent = new Intent(ChooseFavorActivity.this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
    }
    public void setFAVOR(){
        DBHelper db = new DBHelper(ChooseFavorActivity.this,"stock_news.db",1);
        SQLiteDatabase favordb = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < button.length; i++) {
            if (selected[i] == true) {
                cv.put("uid",TEST_UID);
                cv.put("favor",FAVOR[i]);
                favordb.insert("User_Favor", null, cv);
                cv.clear();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}