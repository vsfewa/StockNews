package com.example.stocknews;

import java.io.Serializable;
import java.util.List;

public class HotNews implements Serializable {
    public String title;
    public String ctime;
    public String summary;
    public String url;
    public String stocks;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStocks() {
        return stocks;
    }

    public void setStocks(String stocks) {
        this.stocks = stocks;
    }

    public HotNews(){

    }

    @Override
    public String toString() {
        return "HotNews{" +
                "title='" + title + '\'' +
                ", ctime='" + ctime + '\'' +
                ", summary='" + summary + '\'' +
                ", url='" + url + '\'' +
                ", stocks='" + stocks + '\'' +
                '}';
    }

    public HotNews(String title, String ctime, String summary, String url, String stocks) {
        this.title = title;
        this.ctime = ctime;
        this.summary = summary;
        this.url = url;
        this.stocks = stocks;
    }
}
