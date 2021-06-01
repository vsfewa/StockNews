package com.example.stocknews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.widget.Toolbar;

import static com.example.stocknews.OkHttpSSL.HotnewsList;


public class WebBrowserActivity extends AppCompatActivity {
    private WebView webView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);
        webView = findViewById(R.id.browser);
        toolbar = findViewById(R.id.toolbar);
        Intent i = getIntent();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        int position = i.getIntExtra("position",1);
        if(HotnewsList.get(position).url!=null){
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            //加载需要显示的网页
            webView.loadUrl(HotnewsList.get(position).url);
        }
    }
}