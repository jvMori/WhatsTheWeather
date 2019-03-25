package com.example.jvmori.myweatherapp.application;

import android.app.Application;

import com.example.jvmori.myweatherapp.data.network.ApixuApi;
import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;

public class WeatherApplication extends Application
{
    private ApixuApi apixuApi;
    private WeatherRepository weatherRepository;



    public ApixuApi getApixuApi(){
        return apixuApi;
    }

    public WeatherRepository weatherRepository(){
        return weatherRepository;
    }
}
