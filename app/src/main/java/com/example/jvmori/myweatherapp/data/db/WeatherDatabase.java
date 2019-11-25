package com.example.jvmori.myweatherapp.data.db;

import com.example.jvmori.myweatherapp.data.current.CurrentWeatherDao;
import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ForecastEntry.class, CurrentWeatherResponse.class}, version = 13, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract ForecastDao forecastDao();
    public abstract CurrentWeatherDao currentWeatherDao();
}
