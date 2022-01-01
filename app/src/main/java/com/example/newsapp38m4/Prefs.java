package com.example.newsapp38m4;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs (Context context) {
        preferences = context.getSharedPreferences("settings", 0);
    }

    public void saveBoardState() {
        preferences.edit().putBoolean("isShown", true).apply();
    }

    public boolean isBoardShown() {
        return preferences.getBoolean("isShown", false);
    }

    //

    public void saveProfileAvatar(Uri AvatarURI) {
        preferences.edit().putString("putAvatar", AvatarURI.toString()).apply();
    }

    public String loadProfileAvatar() {
        return preferences.getString("putAvatar", "");
    }

    //

    public void saveProfileName(String name) {
        preferences.edit().putString("putName", name).apply();
    }

    public String loadProfileName() {
        return preferences.getString("putName", "");
    }

    //

    public void saveProfileSurname(String surname) {
        preferences.edit().putString("putSurname", surname).apply();
    }

    public String loadProfileSurname() {
        return preferences.getString("putSurname", "");
    }
}
