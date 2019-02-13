
package com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast;

import java.util.List;

import com.example.jvmori.myweatherapp.architectureComponents.util.ListTypeConverter;
import com.google.gson.annotations.SerializedName;

import androidx.room.TypeConverters;

public class Forecast {

    @SerializedName("forecastday")
    @TypeConverters(ListTypeConverter.class)
    private List<FutureWeather> mFutureWeather;

    public List<FutureWeather> getForecastday() {
        return mFutureWeather;
    }

    public void setForecastday(List<FutureWeather> futureWeather) {
        mFutureWeather = futureWeather;
    }

}
