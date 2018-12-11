package com.example.jvmori.myweatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.CalendarContract;

import com.example.jvmori.myweatherapp.model.Locations;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class SaveManager
{
    private static final String DATA_NAME = "locations";

    public static void saveData(Context context,ArrayList<Locations> locations) {
        SaveTime(locations);
        SharedPreferences sharedPreferences = context.getSharedPreferences("Locations", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(locations);
        editor.putString(DATA_NAME, json);
        editor.apply();
    }

    public static ArrayList<Locations> loadData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Locations", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(DATA_NAME, null);
        Type type = new TypeToken<ArrayList<Locations>>() {}.getType();
        ArrayList<Locations> list = gson.fromJson(json, type);
        if (list != null){
            return list;
        }
        return new ArrayList<>();
    }

    private static void SaveTime(ArrayList<Locations>  locations){
        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis();
        for (Locations loc: locations) {
            loc.setUpdateTime(time);
        }
    }
}
