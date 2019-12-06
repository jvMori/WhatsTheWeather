package com.example.jvmori.myweatherapp.data.current;

import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;

import io.reactivex.Maybe;
import io.reactivex.Single;

public interface CurrentWeatherNetworkDataSource
{
    Single<CurrentWeatherResponse> getCurrentWeatherByCity(String city);
    Maybe<CurrentWeatherResponse> getCurrentWeatherByGeographic(String latitude, String longitude);
}
