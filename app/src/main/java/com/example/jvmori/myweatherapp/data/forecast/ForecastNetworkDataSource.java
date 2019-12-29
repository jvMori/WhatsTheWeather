package com.example.jvmori.myweatherapp.data.forecast;

import android.location.Location;

import com.example.jvmori.myweatherapp.data.forecast.response.ForecastResponse;

import io.reactivex.Single;

public interface ForecastNetworkDataSource {
    Single<ForecastResponse> getForecast(String cityName);
    Single<ForecastResponse> getForecastByGeo(Location location);
}
