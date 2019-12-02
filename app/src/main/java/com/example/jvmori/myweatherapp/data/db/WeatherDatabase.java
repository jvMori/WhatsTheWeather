package com.example.jvmori.myweatherapp.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.jvmori.myweatherapp.data.current.CurrentWeatherDao;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherUI;
import com.example.jvmori.myweatherapp.data.forecast.ForecastDao;
import com.example.jvmori.myweatherapp.data.forecast.ForecastUI;

@Database(entities = {ForecastUI.class, CurrentWeatherUI.class}, version = 15, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract ForecastDao forecastDao();
    public abstract CurrentWeatherDao currentWeatherDao();
}
