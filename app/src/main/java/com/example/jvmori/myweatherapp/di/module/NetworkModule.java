package com.example.jvmori.myweatherapp.di.module;

import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSourceImpl;

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
}
