package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.current.CurrentWeatherEntry;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.architectureComponents.util.WeatherParameters;

import androidx.lifecycle.LiveData;
import retrofit2.Call;

public interface WeatherNetworkDataSource
{
    Call<CurrentWeatherEntry> fetchWeather(WeatherParameters weatherParameters);
    Call<ForecastEntry> fetchForecast(WeatherParameters weatherParameters);
}
