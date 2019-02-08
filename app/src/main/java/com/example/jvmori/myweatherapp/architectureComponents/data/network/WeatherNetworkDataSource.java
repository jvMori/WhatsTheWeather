package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;

import androidx.lifecycle.LiveData;


public interface WeatherNetworkDataSource
{
    LiveData<CurrentWeatherResponse> currentWeather = null;
    void fetchWeather(String location, String lang);
}
