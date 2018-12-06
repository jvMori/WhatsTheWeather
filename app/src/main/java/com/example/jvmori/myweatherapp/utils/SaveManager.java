package com.example.jvmori.myweatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.jvmori.myweatherapp.model.Locations;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class SaveManager
{
    private static final String DATA_NAME = "locations";

    public static void saveData(Context context,ArrayList<Locations> locations) {
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
        if (list != null)
            return list;

        return new ArrayList<>();
    }


}
