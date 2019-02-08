package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//http://api.apixu.com/v1/current.json?key=7a5ba9d2d18041f38e0135842190602&q=Paris
public interface ApixuApi
{
    @GET("current.json")
    Call<CurrentWeatherResponse> getCurrentWeather(@Query("q") String location, @Query("lang") String language);
}
