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
    public static void saveToList(SharedPreferences pref, Locations location, String name){
        SharedPreferences.Editor editor = pref.edit();
        Set<String> set = pref.getStringSet(name, null);
        if (set == null)
            set = new HashSet<>();
        else
            set.add(location.getId());
        editor.putStringSet(name, set);
        editor.apply();
        editor.commit();
    }

    public static ArrayList<String> getArrayList(SharedPreferences pref, String name){
        Set<String> set = pref.getStringSet(name, null);
        if (set != null && set.size() > 0)
            return new ArrayList<>(set);
        else return null;
    }


}
