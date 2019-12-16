package com.example.jvmori.myweatherapp.data.current;

import android.location.Location;

import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.data.network.Api;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CurrentWeatherNetworkDataSourceImpl implements CurrentWeatherNetworkDataSource {

    private Api api;

    @Inject
    public CurrentWeatherNetworkDataSourceImpl(Api api) {
        this.api = api;
    }

    @Override
    public Single<CurrentWeatherResponse> getCurrentWeatherByCity(String city) {
        return api.getCurrentWeatherByCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<CurrentWeatherResponse> getCurrentWeatherByGeographic(Location location) {
        return api.getCurrentWeatherByGeographic(
                    Double.toString(location.getLatitude()),
                    Double.toString(location.getLongitude()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
