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

    // ============ OLD ============

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

    // ============ NEW ============

    // Name/Surname
    public void saveProfileUsername(String username) {
        preferences.edit().putString("putUsername", username).apply();
    }

    public String loadProfileUsername() {
        return preferences.getString("putUsername", "");
    }

    // Email
    public void saveProfileEmail(String email) {
        preferences.edit().putString("putEmail", email).apply();
    }

    public String loadProfileEmail() {
        return preferences.getString("putEmail", "");
    }

    // Phone
    public void saveProfilePhone(String phone) {
        preferences.edit().putString("putPhone", phone).apply();
    }

    public String loadProfilePhone() {
        return preferences.getString("putPhone", "");
    }

    // Gender
    public void saveProfileGender(String gender) {
        preferences.edit().putString("putGender", gender).apply();
    }

    public String loadProfileGender() {
        return preferences.getString("putGender", "");
    }

    // Birthday
    public void saveProfileBirthday(String birthday) {
        preferences.edit().putString("putBirthday", birthday).apply();
    }

    public String loadProfileBirthday() {
        return preferences.getString("putBirthday", "");
    }
}
