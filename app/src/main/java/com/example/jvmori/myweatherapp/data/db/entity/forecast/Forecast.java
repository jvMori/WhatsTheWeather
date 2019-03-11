
package com.example.jvmori.myweatherapp.data.db.entity.forecast;

import java.util.List;

import com.example.jvmori.myweatherapp.util.ListTypeConverter;
import com.google.gson.annotations.SerializedName;

import androidx.room.TypeConverters;

public class Forecast {

    @SerializedName("forecastday")
    @TypeConverters(ListTypeConverter.class)
    public List<FutureWeather> mFutureWeather;

}
