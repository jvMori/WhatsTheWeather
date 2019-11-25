package com.example.jvmori.myweatherapp.data.current;

import android.util.Log;

import com.example.jvmori.myweatherapp.data.NetworkBoundResource;
import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.data.network.Api;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CurrentWeatherRepositoryImpl implements CurrentWeatherRepository {

    private Api api;
    private CurrentWeatherDao dao;

    @Inject
    public CurrentWeatherRepositoryImpl(Api api, CurrentWeatherDao dao) {
        this.api = api;
        this.dao = dao;
    }

    @Override
    public Flowable<CurrentWeatherUI> getCurrentWeatherByCity(String city) {
        return new NetworkBoundResource<CurrentWeatherUI, CurrentWeatherResponse>() {

            @Override
            protected CurrentWeatherResponse saveCallResult(CurrentWeatherResponse item) {
                Completable.fromAction(() ->
                        dao.insert(item))
                        .subscribeOn(Schedulers.io())
                        .subscribe();
                return item;
            }

            @Override
            protected CurrentWeatherUI mapData(CurrentWeatherResponse currentWeatherResponse) {
                return dataMapper(currentWeatherResponse);
            }

            @Override
            protected boolean isDataFresh(CurrentWeatherResponse data) {
                String date = SimpleDateFormat.getInstance().format(new Date(data.getDt()));
                //return  data != null && System.currentTimeMillis() - data.getDt() < 600000; //10 minutes
                return true;
            }

            @Override
            protected Maybe<CurrentWeatherResponse> loadFromDb() {
                return dao.getCurrentWeatherByCity(city)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io());
            }

            @Override
            protected Maybe<CurrentWeatherResponse> fetchFromNetwork() {
                return api.getCurrentWeatherByCity(city)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        }.getResult();
    }

    @Override
    public Maybe<CurrentWeatherResponse> getCurrentWeatherByGeographic(String latitude, String longitude) {
        return null;
    }

    private CurrentWeatherUI dataMapper(CurrentWeatherResponse response) {
        if (response != null) {
            return new CurrentWeatherUI(
                    response.getDt(),
                    response.getName(),
                    response.getSys().getCountry(),
                    response.getWeather().get(0).getMain(),
                    response.getWeather().get(0).getDescription(),
                    response.getWeather().get(0).getIcon(),
                    response.getMain().getTemp().toString(),
                    response.getMain().getPressure().toString(),
                    response.getMain().getHumidity().toString(),
                    response.getMain().getTempMin().toString(),
                    response.getMain().getTempMin().toString(),
                    response.getWind().getDeg().toString(),
                    response.getWind().getSpeed().toString()
            );
        }
        return null;
    }
}
