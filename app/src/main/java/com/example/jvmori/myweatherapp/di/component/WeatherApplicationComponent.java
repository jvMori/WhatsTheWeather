package com.example.jvmori.myweatherapp.di.component;

import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;
import com.example.jvmori.myweatherapp.di.module.DataSourceModule;
import dagger.Component;

@Component(modules = {DataSourceModule.class})
public interface WeatherApplicationComponent
{
    WeatherRepository weatherRepository();
}
