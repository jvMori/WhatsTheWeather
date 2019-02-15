package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.SearchResponse;
import com.example.jvmori.myweatherapp.architectureComponents.util.WeatherParameters;

import androidx.lifecycle.LiveData;
import retrofit2.Call;

public interface WeatherNetworkDataSource
{
    Call<ForecastEntry> fetchForecast(WeatherParameters weatherParameters);
    LiveData<SearchResponse> searchCity(String cityName);
}
