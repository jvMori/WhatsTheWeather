package com.example.jvmori.myweatherapp.data.forecast;

import com.example.jvmori.myweatherapp.data.forecast.response.ForecastResponse;
import com.example.jvmori.myweatherapp.data.network.Api;

import javax.inject.Inject;

import io.reactivex.Single;

public class ForecastNetworkDataSourceImpl implements ForecastNetworkDataSource {

    private Api api;

    @Inject
    public ForecastNetworkDataSourceImpl(Api api) {
        this.api = api;
    }

    @Override
    public Single<ForecastResponse> getForecast(String cityName) {
        return api.getForecast(cityName);
    }
}
