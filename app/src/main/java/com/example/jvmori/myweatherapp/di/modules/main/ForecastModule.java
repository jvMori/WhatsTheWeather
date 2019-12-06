package com.example.jvmori.myweatherapp.di.modules.main;

import com.example.jvmori.myweatherapp.data.db.WeatherDatabase;
import com.example.jvmori.myweatherapp.data.forecast.ForecastDao;
import com.example.jvmori.myweatherapp.data.forecast.ForecastNetworkDataSource;
import com.example.jvmori.myweatherapp.data.forecast.ForecastNetworkDataSourceImpl;
import com.example.jvmori.myweatherapp.data.forecast.ForecastRepository;
import com.example.jvmori.myweatherapp.data.forecast.ForecastRepositoryImpl;
import com.example.jvmori.myweatherapp.data.network.Api;
import com.example.jvmori.myweatherapp.di.scope.MainActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ForecastModule {

    @MainActivityScope
    @Provides
    public ForecastNetworkDataSource provideForecastNetworkDataSource(Api api){
        return new ForecastNetworkDataSourceImpl(api);
    }

    @MainActivityScope
    @Provides
    public ForecastDao provideForecastDao(WeatherDatabase weatherDatabase){
        return weatherDatabase.forecastDao();
    }

    @MainActivityScope
    @Provides
    public ForecastRepository forecastRepository(ForecastNetworkDataSource networkDataSource, ForecastDao forecastDao){
        return new ForecastRepositoryImpl(networkDataSource, forecastDao);
    }
}
