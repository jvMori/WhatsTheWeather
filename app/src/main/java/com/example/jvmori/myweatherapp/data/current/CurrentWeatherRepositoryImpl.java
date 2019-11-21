package com.example.jvmori.myweatherapp.data.current;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.jvmori.myweatherapp.data.NetworkBoundResource;
import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.data.network.Api;
import com.example.jvmori.myweatherapp.ui.Resource;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;
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
    public LiveData<CurrentWeatherUI> getCurrentWeatherByCity(String city) {
        return new NetworkBoundResource<CurrentWeatherUI, CurrentWeatherResponse>(){

            @Override
            protected void saveCallResult(CurrentWeatherUI item) {

            }

            @Override
            protected boolean shouldFetch(CurrentWeatherUI data) {
                return false;
            }

            @Override
            protected LiveData<CurrentWeatherUI> loadFromDb() {
                return null;
            }

            @Override
            protected LiveData<Resource<CurrentWeatherResponse>> createCall() {
                return null;
            }

            @Override
            protected void onFetchFailed() {

            }

            @Override
            protected LiveData<CurrentWeatherUI> asLiveData() {
                return null;
            }
        }.asLiveData();
    }

    @Override
    public Maybe<CurrentWeatherResponse> getCurrentWeatherByGeographic(String latitude, String longitude) {
        return null;
    }
}
