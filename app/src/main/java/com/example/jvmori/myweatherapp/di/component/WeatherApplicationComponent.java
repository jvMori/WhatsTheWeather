package com.example.jvmori.myweatherapp.di.component;

import com.example.jvmori.myweatherapp.data.network.ApixuApi;
import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;

import dagger.Component;

@Component
public interface WeatherApplicationComponent
{
    WeatherRepository weatherRepository();
    ApixuApi apixuApi();
}
