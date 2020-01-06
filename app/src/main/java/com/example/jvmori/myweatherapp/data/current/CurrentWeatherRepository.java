package com.example.jvmori.myweatherapp.data.current;

import android.location.Location;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public interface CurrentWeatherRepository {
    Flowable<CurrentWeatherUI> getCurrentWeatherByCity(String city);
    Flowable<CurrentWeatherUI> getCurrentWeatherByGeographic(Location location);
    Observable<List<CurrentWeatherUI>> getAllWeather();
    void cleanup();
}
