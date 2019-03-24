package com.example.jvmori.myweatherapp.di.module;

import com.example.jvmori.myweatherapp.data.db.ForecastDao;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSourceImpl;
import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    WeatherNetworkDataSource providesDataSource(){
        return new WeatherNetworkDataSourceImpl();
    }

    @Provides
    @Singleton
    WeatherRepository providesWeatherRepository(WeatherNetworkDataSource weatherNetworkDataSource, ForecastDao forecastDao){
        return new WeatherRepository(weatherNetworkDataSource, forecastDao);
    }
}
