package com.example.jvmori.myweatherapp.util;

import androidx.room.TypeConverter;

import com.example.jvmori.myweatherapp.data.current.response.Description;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class DescriptionTypeConverter
{
    static Gson gson = new Gson();

    @TypeConverter
    public static List<Description> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Description>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectListToString(List<Description> someObjects) {
        return gson.toJson(someObjects);
    }
}
