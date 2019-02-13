package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.current.CurrentWeatherEntry;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.ForecastEntry;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//http://api.apixu.com/v1/current.json?key=7a5ba9d2d18041f38e0135842190602&q=Paris
public interface ApixuApi
{
    @GET("current.json")
    Call<CurrentWeatherEntry> getCurrentWeather(@Query("q") String location, @Query("lang") String language);

    @GET("forecast.json")
    Call<ForecastEntry> getForecast(@Query("q") String location, @Query("lang") String lang, @Query("days") String days);
}
