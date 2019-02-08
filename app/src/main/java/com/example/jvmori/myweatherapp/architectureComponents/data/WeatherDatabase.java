package com.example.jvmori.myweatherapp.architectureComponents.data;

import android.content.Context;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeather;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CurrentWeather.class}, version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {

    public static WeatherDatabase instance;
    public abstract WeatherDao weatherDao();

    public static synchronized WeatherDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class, "weather_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
