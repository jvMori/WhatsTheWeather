package com.example.jvmori.myweatherapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SaveManager
{
    private static String prefName;
    private SharedPreferences pref;

    public SaveManager(Context context, Activity activity, String prefName){
        this.prefName = prefName;
        this.pref = context.getSharedPreferences(this.prefName, activity.MODE_PRIVATE);
    }

    public String getValue(String key){
        return pref.getString(key, "");
    }

    public void saveValue(String key, String value){
        pref.edit().putString(key, value).apply();
    }


}
