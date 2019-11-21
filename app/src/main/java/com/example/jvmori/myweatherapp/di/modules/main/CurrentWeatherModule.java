package com.example.jvmori.myweatherapp.di.modules.main;

import com.example.jvmori.myweatherapp.data.current.CurrentWeatherDao;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherNetworkDataSourceImpl;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherRepository;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherRepositoryImpl;
import com.example.jvmori.myweatherapp.data.db.WeatherDatabase;
import com.example.jvmori.myweatherapp.di.scope.MainActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class CurrentWeatherModule {

    @Binds
    @MainActivityScope
    public abstract CurrentWeatherNetworkDataSource currentWeatherNetworkDataSource (CurrentWeatherNetworkDataSourceImpl currentWeatherNetworkDataSource);

    @Binds
    @MainActivityScope
    public abstract CurrentWeatherRepository currentWeatherRepository (CurrentWeatherRepositoryImpl currentWeatherRepositoryImpl);

    @Provides
    @MainActivityScope
    public CurrentWeatherDao currentWeatherDao(WeatherDatabase weatherDatabase){
        return weatherDatabase.currentWeatherDao();
    }

}
