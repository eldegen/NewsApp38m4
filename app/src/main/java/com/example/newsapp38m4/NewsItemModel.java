package com.example.newsapp38m4;

public class NewsItemModel {
    private String newsTitle;
    private long newsDate;

    public NewsItemModel(String newsTitle, long newsDate) {
        this.newsTitle = newsTitle;
        this.newsDate = newsDate;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public long getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(long newsDate) {
        this.newsDate = newsDate;
    }
}
