package com.example.jvmori.myweatherapp.data.current;

import androidx.lifecycle.LiveData;

import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.ui.Resource;

import io.reactivex.Maybe;

public interface CurrentWeatherRepository {
    LiveData<Resource<CurrentWeatherUI>> getCurrentWeatherByCity(String city);
    Maybe<CurrentWeatherResponse> getCurrentWeatherByGeographic( String latitude, String longitude);
}
