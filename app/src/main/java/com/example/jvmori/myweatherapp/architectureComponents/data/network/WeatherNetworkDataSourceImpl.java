package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.util.WeatherParameters;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.current.CurrentWeatherEntry;
import retrofit2.Call;


public class WeatherNetworkDataSourceImpl  {

    public Call<CurrentWeatherEntry> fetchWeather(WeatherParameters weatherParameters){
        ApixuApi service = ApixuApiCall.init();
        return service.getCurrentWeather(weatherParameters.getLocation(), weatherParameters.getLang());
    }
}
