package com.example.jvmori.myweatherapp.architectureComponents.data;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeather;

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
    LiveData<CurrentWeather> getWeather();

}
