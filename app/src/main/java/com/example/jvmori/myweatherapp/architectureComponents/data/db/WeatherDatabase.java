package com.example.jvmori.myweatherapp.architectureComponents.data.db;

import android.content.Context;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.current.CurrentWeatherEntry;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.ForecastEntry;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CurrentWeatherEntry.class, ForecastEntry.class}, version = 8, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {

    public static WeatherDatabase instance;
    public abstract CurrentWeatherDao weatherDao();
    public abstract ForecastDao forecastDao();

    public static synchronized WeatherDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class, "weather_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
