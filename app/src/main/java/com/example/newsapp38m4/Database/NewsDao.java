package com.example.newsapp38m4.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.newsapp38m4.ui.news.NewsItemModel;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM newsitemmodel")
    List<NewsItemModel> getAll();

    @Insert
    void insert(NewsItemModel newsItemModel);
}
