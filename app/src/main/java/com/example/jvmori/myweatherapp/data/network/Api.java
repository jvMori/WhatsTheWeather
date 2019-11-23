package com.example.jvmori.myweatherapp.data.network;

import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.ui.Resource;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api
{
    @GET("forecast.json")
    Maybe<ForecastEntry> getForecast(@Query("q") String location, @Query("lang") String lang, @Query("days") String days);

    @GET("search.json")
    Observable<List<Search>> searchCity (@Query("q") String cityName);

    @GET("weather")
    Maybe<CurrentWeatherResponse> getCurrentWeatherByCity(@Query("q") String city);

    @GET("weather")
    Maybe<CurrentWeatherResponse> getCurrentWeatherByGeographic(@Query("lat") String latitude, @Query("lon") String longitude);

}
