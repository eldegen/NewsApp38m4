package com.example.newsapp38m4.ui.news;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class NewsItemModel implements Serializable {
    private String newsTitle;
    private long newsDate;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public NewsItemModel(String newsTitle, long newsDate) {
        this.newsTitle = newsTitle;
        this.newsDate = newsDate;
    }

    // Database
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // News Title
    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    // News date
    public long getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(long newsDate) {
        this.newsDate = newsDate;
    }
}
