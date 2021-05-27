package com.example.stocknews;



import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
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

public class MainActivity extends AppCompatActivity {

    private final String HotNewsUrl = "https://m.10jqka.com.cn/todayhot.json";
    private List<Integer> colors = new ArrayList<>();
    private List<String> type = new ArrayList<>();
    public static List<HotNews>  HotnewsList = new ArrayList<>();
    {
        colors.add(android.R.color.holo_blue_bright);
        colors.add(android.R.color.holo_purple);
    }


    private ViewPager2 viewPager ;
    private TabLayout column;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        type.add(this.getString(R.string.hot_news));
        type.add(this.getString(R.string.roll_news));
        sendRequestwithOkHttp();
        viewPager = findViewById(R.id.Viewpager);
        HotnewsPageFragment.ViewPagerFragmentStateAdapter viewPagerAdapter = null;
        try {
            viewPagerAdapter = new HotnewsPageFragment.ViewPagerFragmentStateAdapter(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        viewPager.setAdapter(viewPagerAdapter);
        column = findViewById(R.id.Column);
        new TabLayoutMediator(column, viewPager, true,new TabLayoutMediator.TabConfigurationStrategy(){
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(type.get(position));
            }
        }).attach();
    }


    private void sendRequestwithOkHttp(){
        new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        try {
                            OkHttpClient client = OkHttpSSL.getUnsafeOkHttpClient();;
                            Request request = new Request.Builder()
                                    .url(HotNewsUrl)
                                    .get()
                                    .build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string().replace("{\"pageItems\":","").replace(",\"columnName\":\"\\u4eca\\u65e5\\u8981\\u95fb\",\"pages\":1,\"total\":35,\"currentPage\":1}","");
                            Log.d("MainActivity1",responseData);
                            HotnewsList=parseJsonWithGson(responseData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (connection != null) {
                                connection.disconnect();
                            }
                        }
                    }
                }).start();
    }
    private List<HotNews> parseJsonWithGson(String jsonData) {
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        List<HotNews> HotnewsList = gson.fromJson(jsonData, new TypeToken<List<HotNews>>() {}.getType());
        for (HotNews news: HotnewsList){
            Log.d("MainActivity","title"+news.getTitle());
            Log.d("MainActivity4","ctime"+news.getCtime());
            Log.d("MainActivity7","summary"+news.getSummary());
        }
        return HotnewsList;
    }


}
