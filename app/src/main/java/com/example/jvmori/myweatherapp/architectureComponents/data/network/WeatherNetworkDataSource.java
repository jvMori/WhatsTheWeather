package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeatherEntry;

import androidx.lifecycle.LiveData;

public interface WeatherNetworkDataSource
{
    LiveData<CurrentWeatherEntry> currentWeather();
    void fetchWeather(String location, String lang);

}
