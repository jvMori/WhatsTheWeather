package com.example.jvmori.myweatherapp.data.forecast;

import android.location.Location;

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

    @Override
    public Single<ForecastResponse> getForecastByGeo(Location location) {
        return api.getForecastByGeographic(Double.toString(location.getLatitude()), Double.toString(location.getLongitude()));
    }
}
