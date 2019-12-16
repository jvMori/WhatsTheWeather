package com.example.jvmori.myweatherapp.data.current;

import android.location.Location;

import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface CurrentWeatherNetworkDataSource
{
    Single<CurrentWeatherResponse> getCurrentWeatherByCity(String city);
    Single<CurrentWeatherResponse> getCurrentWeatherByGeographic(Location location);
}
