package com.example.jvmori.myweatherapp.data.forecast;

import android.location.Location;

import io.reactivex.Flowable;

public interface ForecastRepository {
    Flowable<Forecasts>getForecast(String location);
    Flowable<Forecasts> getForecastByGeo(Location location);
    void delete(String city);
}
