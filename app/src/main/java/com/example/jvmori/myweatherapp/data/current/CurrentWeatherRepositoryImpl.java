package com.example.jvmori.myweatherapp.data.current;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.Transformations;

import com.example.jvmori.myweatherapp.data.NetworkBoundResource;
import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.data.network.Api;
import com.example.jvmori.myweatherapp.ui.Resource;

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
    public LiveData<Resource<CurrentWeatherUI>> getCurrentWeatherByCity(String city) {
        String _city = city;
        return new NetworkBoundResource<CurrentWeatherUI, CurrentWeatherResponse>() {

            @Override
            protected void saveCallResult(CurrentWeatherResponse item) {
                Completable.fromAction(() -> dao.insert(item)).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe();
            }

            @Override
            protected boolean shouldFetch(CurrentWeatherUI data) {
                return data == null || (System.currentTimeMillis() - data.getTimestamp() < 3600);
            }

            @Override
            protected LiveData<CurrentWeatherUI> loadFromDb() {
                return Transformations.map(
                        dao.getCurrentWeatherByCity(_city),
                        response ->
                                dataMapper(response)
                );
            }

            @Override
            protected LiveData<Resource<CurrentWeatherResponse>> createCall() {
                return LiveDataReactiveStreams.fromPublisher(
                        api.getCurrentWeatherByCity(_city)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                        .doOnError(
                                error ->
                                        Log.i("WEATHER", error.getMessage())
                        )
                );
            }

            @Override
            protected void onFetchFailed() {

            }
        }.asLiveData();
    }

    @Override
    public Maybe<CurrentWeatherResponse> getCurrentWeatherByGeographic(String latitude, String longitude) {
        return null;
    }

    private CurrentWeatherUI dataMapper(CurrentWeatherResponse response) {
        if (response != null) {
            return new CurrentWeatherUI(
                    response.getDt(),
                    response.getCityName(),
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
