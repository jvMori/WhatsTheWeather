package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.architectureComponents.util.WeatherParameters;

import retrofit2.Call;


public class WeatherNetworkDataSourceImpl implements WeatherNetworkDataSource  {

    private ApixuApi service;

    public WeatherNetworkDataSourceImpl(){
        service = ApixuApiCall.init();
    }

    @Override
    public Call<ForecastEntry> fetchForecast(WeatherParameters weatherParameters) {
        return service.getForecast(weatherParameters.getLocation(), weatherParameters.getLang(), weatherParameters.getDays());
    }
}
