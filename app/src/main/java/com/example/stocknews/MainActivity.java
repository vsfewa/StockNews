package com.example.stocknews;



import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static com.example.stocknews.OkHttpSSL.getHotnewswithOkHttp;
import static com.example.stocknews.OkHttpSSL.getRollnewswithOkHttp;

public class MainActivity extends AppCompatActivity {


    private List<String> type = new ArrayList<>();
    public static final String RECENT_URL = "https://m.10jqka.com.cn/thsgd_list/index_1.json";

    private ViewPager2 viewPager ;
    private TabLayout column;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        type.add(this.getString(R.string.hot_news));
        type.add(this.getString(R.string.roll_news));
        getHotnewswithOkHttp();
        getRollnewswithOkHttp(RECENT_URL);
        viewPager = findViewById(R.id.Viewpager);
        HotnewsPageFragment.ViewPagerFragmentStateAdapter viewPagerAdapter = null;
        try {
            viewPagerAdapter = new HotnewsPageFragment.ViewPagerFragmentStateAdapter(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        viewPager.setAdapter(viewPagerAdapter);
        column = findViewById(R.id.Column);
        new TabLayoutMediator(column, viewPager, true, (tab, position) -> tab.setText(type.get(position))).attach();
    }




}
