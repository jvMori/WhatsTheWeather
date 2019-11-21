package com.example.jvmori.myweatherapp.data.network;

import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.util.WeatherParameters;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class WeatherNetworkDataSourceImpl implements WeatherNetworkDataSource {

    private Api service;

    @Inject
    public WeatherNetworkDataSourceImpl(Api service) {
        this.service = service;
    }

    @Override
    public Maybe<ForecastEntry> fetchWeather(WeatherParameters weatherParameters) {
        return service.getForecast(
                weatherParameters.getLocation(),
                weatherParameters.getLang(),
                weatherParameters.getDays());
    }

    @Override
    public Observable<List<Search>> searchCity(String cityName) {
        return service.searchCity(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation());
    }
}
