package com.example.jvmori.myweatherapp.data.current;

import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface CurrentWeatherRepository {
    Flowable<CurrentWeatherUI> getCurrentWeatherByCity(String city);
    Maybe<CurrentWeatherResponse> getCurrentWeatherByGeographic( String latitude, String longitude);
}
