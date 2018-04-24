package com.example.news.local.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.news.models.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rim on 16/04/2018.
 */

public class LocalStorageManager {

    private Gson gson;
    private final String USER_KEY = "PROFILE";
    private static LocalStorageManager localStorageManager;
    private SharedPreferences sharedPreferences;

    private LocalStorageManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
    }

    public static LocalStorageManager getInstance(Context context) {
        if (localStorageManager == null) {
            localStorageManager = new LocalStorageManager(context);
        }
        return localStorageManager;
    }

    public User getUser() {
        String userJson = sharedPreferences.getString(USER_KEY, null);
        if (userJson == null) {
            return null;
        }
        try {
            return gson.fromJson(userJson, User.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void saveUser(User user) {
        String userJson = gson.toJson(user);
        sharedPreferences
                .edit()
                .putString(USER_KEY, userJson)
                .commit();
    }

    public void deleteUser() {
        sharedPreferences
                .edit()
                .putString(USER_KEY, null)
                .commit();
    }

}
