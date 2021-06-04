package com.example.stocknews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class SplashActivity extends AppCompatActivity {


    private static final int SPLASH_DELAY_MILLIS = 2000;
    private static final int DATABASE_NULL = 0;
    private static final int DENIED = -1;
    private static final String NOTICE = "为了更好地为您提供服务,请通过权限申请";
    public int sqlnumber;
    private DBHelper myDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermission();
        }
    }


    String[] Permissions = new String[]{Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    List<String> mPermissionList = new ArrayList<>();
    private final int mRequestCode = 100;


    /*
    权限判断和申请
    */
    private void getPermission() {
        mPermissionList.clear();
        /*
        判断权限是否已经通过
         */
        for (String permission : Permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permission);//添加还未授予的权限
            }
        }
        /*
        申请权限
         */
        if (!mPermissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, Permissions, mRequestCode);
        } else {
            new Handler().postDelayed(() -> jump(), SPLASH_DELAY_MILLIS);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;
        if (mRequestCode == requestCode) {
            for (int grantResult : grantResults) {
                if (grantResult == DENIED) {
                    hasPermissionDismiss = true;
                    break;
                }
            }
            /*
            如果有权限没有被允许
             */
            if (hasPermissionDismiss) {
                Toast.makeText(this,
                        NOTICE,
                        Toast.LENGTH_SHORT).show();
                getPermission();
            }else{
                new Handler().postDelayed(() -> jump(), SPLASH_DELAY_MILLIS);
                }
            }
        }

    /*
    根判断用户是否进行偏好选择
     */
    private void jump() {
        Handler mhandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                switch (message.what){
                    case 20:
                        if(sqlnumber==DATABASE_NULL) {
                            Intent intent = new Intent(SplashActivity.this, ChooseFavorActivity.class);
                            SplashActivity.this.startActivity(intent);
                            SplashActivity.this.finish();
                        }
                        else{
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            SplashActivity.this.startActivity(intent);
                            SplashActivity.this.finish();
                        }
                }
                return true;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                sqlnumber = DBHelper.getInstance().check_if_nulldatabase();
                mhandler.sendEmptyMessage(20);
            }
        }).start();
    }

}