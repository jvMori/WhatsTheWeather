package com.example.jvmori.myweatherapp.data.network;

import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.util.WeatherParameters;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;

public interface WeatherNetworkDataSource
{
    Maybe<ForecastEntry> fetchWeather(WeatherParameters weatherParameters);
    Observable<List<Search>> searchCity(String cityName);
}
