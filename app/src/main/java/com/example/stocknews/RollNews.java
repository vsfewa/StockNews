package com.example.stocknews;

/*
24小时滚动新闻数据
 */
public class RollNews {
    public String title;
    public String ctime;
    public String digest;

    public RollNews(String title, String ctime, String digest) {
        this.title = title;
        this.ctime = ctime;
        this.digest = digest;
    }
    public RollNews() {

    }

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

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
}

