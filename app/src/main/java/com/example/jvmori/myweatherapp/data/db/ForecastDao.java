package com.example.jvmori.myweatherapp.data.db;

import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ForecastDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ForecastEntry forecastEntry);

    @Query("delete from forecast where isDeviceLocation = 1")
    void deleteForecastForDeviceLocation();

    @Query("delete from forecast where mCityName like :cityName")
    void deleteForecastByLocation(String cityName);

    @Query("select * from forecast")
    LiveData<List<ForecastEntry>> getForecastsForAllLocations();

    @Query("select * from forecast where mCityName like :location")
    ForecastEntry getForecastsForLocation(String location);

    @Query("select * from forecast where isDeviceLocation = 0")
    LiveData<List<ForecastEntry>> allForecastsExceptForDeviceLocation();

}
