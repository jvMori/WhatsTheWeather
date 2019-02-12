package com.example.jvmori.myweatherapp.architectureComponents.data;

import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface WeatherDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CurrentWeatherResponse currentWeather);

    @Query("select * from current_weather where isDeviceLocation like 0" )
    LiveData<List<CurrentWeatherResponse>> getWeather();

    @Query("select * from current_weather where mCityName like :locationName" )
    LiveData<CurrentWeatherResponse> getWeatherForLocation(String locationName);
}
