package com.example.jvmori.myweatherapp.data.current;

import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.data.network.Api;

import javax.inject.Inject;

import io.reactivex.Maybe;

public class CurrentWeatherNetworkDataSourceImpl implements CurrentWeatherNetworkDataSource {

    private Api api;

    @Inject
    public CurrentWeatherNetworkDataSourceImpl(Api api) {
        this.api = api;
    }

    @Override
    public Maybe<CurrentWeatherResponse> getCurrentWeatherByCity(String city) {
        return api.getCurrentWeatherByCity(city);
    }

    @Override
    public Maybe<CurrentWeatherResponse> getCurrentWeatherByGeographic(String latitude, String longitude) {
        return api.getCurrentWeatherByGeographic(latitude, longitude);
    }
}
