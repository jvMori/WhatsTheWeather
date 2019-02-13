package com.example.jvmori.myweatherapp.architectureComponents.util;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.FutureWeather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;

public class ListTypeConverter<T>
{
    Gson gson = new Gson();

    @TypeConverter
    public List<T> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<T>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectListToString(List<T> someObjects) {
        return gson.toJson(someObjects);
    }
}
