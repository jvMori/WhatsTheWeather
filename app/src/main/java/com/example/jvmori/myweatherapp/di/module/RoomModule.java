package com.example.jvmori.myweatherapp.di.module;

import android.app.Application;

import com.example.jvmori.myweatherapp.data.db.ForecastDao;
import com.example.jvmori.myweatherapp.data.db.WeatherDatabase;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    private WeatherDatabase weatherDatabase;

    public RoomModule (Application application){
        weatherDatabase = Room.databaseBuilder(application.getApplicationContext(), WeatherDatabase.class, "weather_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    WeatherDatabase weatherDatabase(){
        return weatherDatabase;
    }

    @Singleton
    @Provides
    ForecastDao forecastDao(WeatherDatabase weatherDatabase){
        return weatherDatabase.forecastDao();
    }

}
