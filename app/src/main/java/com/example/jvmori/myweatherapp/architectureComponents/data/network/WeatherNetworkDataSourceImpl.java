package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherNetworkDataSourceImpl implements WeatherNetworkDataSource{

    public Observable<CurrentWeatherResponse> fetchWeather(String location, String lang){
        ApixuApi service = ApixuApiCall.init();
        return service.getCurrentWeather(location, lang)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
