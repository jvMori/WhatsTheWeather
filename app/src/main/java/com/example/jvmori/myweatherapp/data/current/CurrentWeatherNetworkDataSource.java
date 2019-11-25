package com.example.jvmori.myweatherapp.data.current;

import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.ui.Resource;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import retrofit2.http.Query;

public interface CurrentWeatherNetworkDataSource
{
    Maybe<CurrentWeatherResponse> getCurrentWeatherByCity(@Query("q") String city);
    Maybe<CurrentWeatherResponse> getCurrentWeatherByGeographic(@Query("lat") String latitude, @Query("lon") String longitude);
}
