package com.example.jvmori.myweatherapp.application;

import android.app.Application;
import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;
import com.example.jvmori.myweatherapp.di.component.DaggerWeatherApplicationComponent;
import com.example.jvmori.myweatherapp.di.component.WeatherApplicationComponent;
import com.example.jvmori.myweatherapp.di.module.ContextModule;

public class WeatherApplication extends Application
{
    private WeatherRepository weatherRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        WeatherApplicationComponent component = DaggerWeatherApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        weatherRepository = component.weatherRepository();
    }

    public WeatherRepository weatherRepository(){
        return weatherRepository;
    }
}
