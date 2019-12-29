package com.example.jvmori.myweatherapp.data.forecast;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Maybe;

@Dao
public abstract class ForecastDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Forecasts forecastEntry);

    @Query("delete from forecast_table where city_name like :cityName")
    public abstract void deleteForecast(String cityName);

    @Query("select * from forecast_table where city_name like :cityName")
    public abstract Maybe<Forecasts> getForecast(String cityName);

    @Query("select * from forecast_table where latitude like :lat AND longitude like :lon")
    public abstract Maybe<Forecasts> getForecastByGeo(String lat, String lon);

}
