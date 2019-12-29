package com.example.jvmori.myweatherapp.util;

import androidx.room.TypeConverter;

import com.example.jvmori.myweatherapp.data.forecast.ForecastEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ListTypeConverter
{
    static Gson gson = new Gson();

    @TypeConverter
    public static List<ForecastEntity> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<ForecastEntity>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectListToString(List<ForecastEntity> someObjects) {
        return gson.toJson(someObjects);
    }
}
