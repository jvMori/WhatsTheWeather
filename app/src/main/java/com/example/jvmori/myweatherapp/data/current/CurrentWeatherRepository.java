package com.example.jvmori.myweatherapp.data.current;

import android.location.Location;

import io.reactivex.Flowable;

public interface CurrentWeatherRepository {
    Flowable<CurrentWeatherUI> getCurrentWeatherByCity(String city);
    Flowable<CurrentWeatherUI> getCurrentWeatherByGeographic(Location location);
    void cleanup();
}
