package com.example.jvmori.myweatherapp.data.network;

import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.data.forecast.response.ForecastResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api
{
    @GET("forecast")
    Single<ForecastResponse> getForecast(@Query("q") String location);

    @GET("forecast")
    Single<ForecastResponse> getForecastByGeographic(@Query("lat") String latitude, @Query("lon") String longitude);

    @GET("weather")
    Single<CurrentWeatherResponse> getCurrentWeatherByCity(@Query("q") String city);

    @GET("weather")
    Single<CurrentWeatherResponse> getCurrentWeatherByGeographic(@Query("lat") String latitude, @Query("lon") String longitude);

}
