package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.data.util.WeatherParameters;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;
import retrofit2.Call;


public class WeatherNetworkDataSourceImpl  {

    public Call<CurrentWeatherResponse> fetchWeather(WeatherParameters weatherParameters){
        ApixuApi service = ApixuApiCall.init();
        return service.getCurrentWeather(weatherParameters.getLocation(), weatherParameters.getLang());
    }
}
