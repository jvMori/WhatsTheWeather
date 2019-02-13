package com.example.jvmori.myweatherapp.architectureComponents.data.db;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.current.CurrentWeatherEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CurrentWeatherDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CurrentWeatherEntry currentWeather);

    @Query("delete from current_weather where isDeviceLocation = 1")
    void deleteLastDeviceLocation();

    @Query("select * from current_weather where isDeviceLocation like 0" )
    LiveData<List<CurrentWeatherEntry>> getWeather();

    @Query("select * from current_weather" )
    LiveData<List<CurrentWeatherEntry>> getAllWeather();

    @Query("select * from current_weather where mCityName like :locationName" )
    LiveData<CurrentWeatherEntry> getWeatherForLocation(String locationName);
}
