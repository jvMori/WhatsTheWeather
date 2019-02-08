package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;

import io.reactivex.Observable;

public interface WeatherNetworkDataSource
{
    Observable<CurrentWeatherResponse> fetchWeather(String location, String lang);
}
