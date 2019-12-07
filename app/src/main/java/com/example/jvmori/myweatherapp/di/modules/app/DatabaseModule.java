package com.example.jvmori.myweatherapp.di.modules.app;

import android.app.Application;

import androidx.room.Room;

import com.example.jvmori.myweatherapp.data.forecast.ForecastDao;
import com.example.jvmori.myweatherapp.data.db.WeatherDatabase;
import com.example.jvmori.myweatherapp.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @ApplicationScope
    public WeatherDatabase weatherDatabase(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class, "weather_database")
                .fallbackToDestructiveMigration()
                .build();
    }
}
