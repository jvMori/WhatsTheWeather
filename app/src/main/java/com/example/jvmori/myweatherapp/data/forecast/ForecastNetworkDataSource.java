package com.example.jvmori.myweatherapp.data.forecast;

import com.example.jvmori.myweatherapp.data.forecast.response.ForecastResponse;

import io.reactivex.Single;

public interface ForecastNetworkDataSource {
    Single<ForecastResponse> getForecast(String cityName);
}
