package com.example.jvmori.myweatherapp.architectureComponents.data;

import android.content.Context;

import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CurrentWeatherResponse.class}, version = 5, exportSchema = false)
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
