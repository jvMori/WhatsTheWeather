package com.example.jvmori.myweatherapp.data.forecast;

import io.reactivex.Flowable;

public interface ForecastRepository {
    Flowable<Forecasts>getForecast(String location);
}
