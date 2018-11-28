package com.example.jvmori.myweatherapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.jvmori.myweatherapp.model.Locations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SaveManager
{
    private SharedPreferences pref;

    public SaveManager(Context context, String prefName){
        this.pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    public String getValue(String key){
        return pref.getString(key, null);
    }

    public void saveValue(String key, String value){
        pref.edit().putString(key, value).apply();
    }

    public void saveList(ArrayList<Locations> arrayList, String name){
        Set<String> set = new HashSet<>();
        for (Locations loc : arrayList) {
            set.add(loc.getId());
        }
        pref.edit().putStringSet(name, set).apply();
    }

    public ArrayList<String> getArrayList(String name){
        Set<String> set = pref.getStringSet(name, null);
        if (set != null)
            return new ArrayList<>(set);
        else return null;
    }
}
