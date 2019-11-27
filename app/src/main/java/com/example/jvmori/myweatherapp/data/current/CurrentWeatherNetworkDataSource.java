package com.example.jvmori.myweatherapp.data.current;

import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;

import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.http.Query;

public interface CurrentWeatherNetworkDataSource
{
    Single<CurrentWeatherResponse> getCurrentWeatherByCity(@Query("q") String city);
    Maybe<CurrentWeatherResponse> getCurrentWeatherByGeographic(@Query("lat") String latitude, @Query("lon") String longitude);
}
