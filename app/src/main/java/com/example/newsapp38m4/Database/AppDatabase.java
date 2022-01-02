package com.example.newsapp38m4.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.newsapp38m4.ui.news.NewsItemModel;

@Database(entities = {NewsItemModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract  NewsDao newsDao();
}
