package com.example.jvmori.myweatherapp.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.jvmori.myweatherapp.data.current.CurrentWeatherDao;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherUI;
import com.example.jvmori.myweatherapp.data.forecast.ForecastDao;
import com.example.jvmori.myweatherapp.data.forecast.Forecasts;
import com.example.jvmori.myweatherapp.util.ListTypeConverter;

@Database(entities = {Forecasts.class, CurrentWeatherUI.class}, version = 24, exportSchema = false)
@TypeConverters(value = ListTypeConverter.class)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract ForecastDao forecastDao();
    public abstract CurrentWeatherDao currentWeatherDao();
}
