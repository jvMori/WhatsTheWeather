package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.Search;
import com.example.jvmori.myweatherapp.architectureComponents.util.WeatherParameters;

import java.util.List;

import androidx.lifecycle.LiveData;
import retrofit2.Call;

public interface WeatherNetworkDataSource
{
    Call<ForecastEntry> fetchForecast(WeatherParameters weatherParameters);
    LiveData<List<Search>> searchCity(String cityName);
}
