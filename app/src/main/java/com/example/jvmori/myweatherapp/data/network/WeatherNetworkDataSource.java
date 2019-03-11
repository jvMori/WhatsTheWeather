package com.example.jvmori.myweatherapp.data.network;

import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.util.WeatherParameters;

import java.util.List;

import androidx.lifecycle.LiveData;
import retrofit2.Call;

public interface WeatherNetworkDataSource
{
    Call<ForecastEntry> fetchForecast(WeatherParameters weatherParameters);
    LiveData<List<Search>> searchCity(String cityName);
}
