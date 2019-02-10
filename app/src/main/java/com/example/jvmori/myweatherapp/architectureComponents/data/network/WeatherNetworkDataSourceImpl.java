package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;
import retrofit2.Call;


public class WeatherNetworkDataSourceImpl  {

    public Call<CurrentWeatherResponse> fetchWeather(String location, String lang){
        ApixuApi service = ApixuApiCall.init();
        return service.getCurrentWeather(location, lang);
    }
}
