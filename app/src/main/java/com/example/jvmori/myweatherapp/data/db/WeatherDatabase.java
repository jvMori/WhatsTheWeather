package com.example.jvmori.myweatherapp.data.db;

import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ForecastEntry.class}, version = 11, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract ForecastDao forecastDao();
}
