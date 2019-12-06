package com.example.jvmori.myweatherapp.data.network;

import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.data.forecast.response.ForecastResponse;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.ui.Resource;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api
{
    @GET("forecast")
    Flowable<ForecastResponse> getForecast(@Query("q") String location);

    @GET("search.json")
    Observable<List<Search>> searchCity (@Query("q") String cityName);

    @GET("weather")
    Single<CurrentWeatherResponse> getCurrentWeatherByCity(@Query("q") String city);

    @GET("weather")
    Maybe<CurrentWeatherResponse> getCurrentWeatherByGeographic(@Query("lat") String latitude, @Query("lon") String longitude);

}
