package com.example.jvmori.myweatherapp.architectureComponents.data;

import com.example.jvmori.myweatherapp.architectureComponents.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;


public class WeatherRepository
{
    private static final Object LOCK = new Object();
    private static WeatherRepository instance;
    private WeatherNetworkDataSource weatherNetworkDataSource;

    private WeatherRepository (WeatherNetworkDataSource weatherNetworkDataSource){
        this.weatherNetworkDataSource = weatherNetworkDataSource;

        weatherNetworkDataSource.currentWeather.observeForever(new Observer<CurrentWeatherResponse>() {
            @Override
            public void onChanged(CurrentWeatherResponse currentWeatherResponse) {

            }
        });
    }

//    public LiveData<CurrentWeatherResponse> getCurrentWeather(String localization, String lang){
//        weatherNetworkDataSource.fetchWeather(localization, lang);
//    }


    public synchronized static WeatherRepository getInstance(WeatherNetworkDataSource weatherNetworkDataSource){
        if (instance == null){
            synchronized (LOCK){
                instance = new WeatherRepository(weatherNetworkDataSource);
            }
        }
        return instance;
    }
}
