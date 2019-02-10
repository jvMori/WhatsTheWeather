package com.example.jvmori.myweatherapp.architectureComponents.data;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeather;

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
    void insert(CurrentWeather currentWeather);

    @Query("select * from current_weather" )
    LiveData<List<CurrentWeather>> getWeather();

    @Query("select * from current_weather where location like :locationName" )
    LiveData<CurrentWeather> getWeatherForLocation(String locationName);
}
