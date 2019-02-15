package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApixuApi
{
    @GET("forecast.json")
    Call<ForecastEntry> getForecast(@Query("q") String location, @Query("lang") String lang, @Query("days") String days);

    @GET("search.json")
    Call<SearchResponse> searchCity (@Query("q") String cityName);
}
