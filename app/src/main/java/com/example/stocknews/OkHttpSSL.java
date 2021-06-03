package com.example.stocknews;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.transform.sax.TemplatesHandler;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 处理证书不受信任的问题
 */
public class OkHttpSSL {
    public static final String HotNewsUrl = "https://m.10jqka.com.cn/todayhot.json";
    public static final String RollNewsPre = "https://m.10jqka.com.cn/thsgd_list/index_";
    public static final String RollNewsPost = ".json";
    public static int RefreshTime = 1;
    static final int PRE_NUM = 77;

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /*
    热点掘金数据请求
     */
    public static List<HotNews> getHotnewswithOkHttp(){
        List<HotNews> HotnewsList = new ArrayList<>();
        HttpURLConnection connection = null;
        try {
            OkHttpClient client = OkHttpSSL.getUnsafeOkHttpClient();
            Request request = new Request.Builder()
                            .url(HotNewsUrl)
                            .get()
                            .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string().replace("{\"pageItems\":","").replace(",\"columnName\":\"\\u4eca\\u65e5\\u8981\\u95fb\",\"pages\":1,\"total\":35,\"currentPage\":1}","");
            Log.d("MainActivity1",responseData);
            HotnewsList= parseHotnewsJsonWithGson(responseData);
        } catch (Exception e) {
                    e.printStackTrace();
        }
        return HotnewsList;
    }
    /*
    24小时滚动新闻数据请求
     */
    public static  List<RollNews> getRollnewswithOkHttp(String tempurl){
        List<RollNews>  RollnewsList = new ArrayList<>();
                HttpURLConnection connection = null;
                try {
                    OkHttpClient client = OkHttpSSL.getUnsafeOkHttpClient();
                    Request request = new Request.Builder()
                            .url(tempurl)
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    int num = PRE_NUM + String.valueOf(RefreshTime).length()-1;
                    String pre=responseData.substring(0,num);
                    responseData = responseData.replace(pre,"");
                    responseData = responseData.substring(0,responseData.length()-1);
                    Log.d("MainActivity2",responseData);
                    List <RollNews> update = parseRollnewsJsonWithGson(responseData);
                    for(RollNews news: update){
                        RollnewsList.add(news);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

        return  RollnewsList;
    }
    public static List<HotNews> parseHotnewsJsonWithGson(String jsonData) {
        /*
        使用轻量级的Gson解析得到的json
         */
        Gson gson = new Gson();
        List<HotNews> HotnewsList = gson.fromJson(jsonData, new TypeToken<List<HotNews>>() {}.getType());
        return HotnewsList;
    }
    public static List<RollNews> parseRollnewsJsonWithGson(String jsonData) {
        Gson gson = new Gson();
        List<RollNews> RollnewsList = gson.fromJson(jsonData, new TypeToken<List<RollNews>>() {}.getType());
        return RollnewsList;
    }
}
