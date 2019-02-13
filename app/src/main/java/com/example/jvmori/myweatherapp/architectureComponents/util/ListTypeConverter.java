package com.example.jvmori.myweatherapp.architectureComponents.util;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.current.CurrentWeather;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.FutureWeather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;

public class ListTypeConverter
{
    static Gson gson = new Gson();

    @TypeConverter
    public static List<FutureWeather> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<FutureWeather>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectListToString(List<FutureWeather> someObjects) {
        return gson.toJson(someObjects);
    }
}
