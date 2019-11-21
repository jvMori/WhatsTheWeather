package com.example.jvmori.myweatherapp.di.modules.main;

import com.example.jvmori.myweatherapp.data.LocationProvider;
import com.example.jvmori.myweatherapp.data.LocationProviderImpl;
import com.example.jvmori.myweatherapp.data.db.ForecastDao;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;
import com.example.jvmori.myweatherapp.di.scope.ApplicationScope;
import com.example.jvmori.myweatherapp.di.scope.MainActivityScope;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.DeleteLocationItem;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.DeleteLocationItemOnSwipe;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.LocationAdapter;
import com.example.jvmori.myweatherapp.ui.viewModel.WeatherViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    @MainActivityScope
    public WeatherRepository weatherRepository(WeatherNetworkDataSource weatherNetworkDataSource, ForecastDao forecastDao) {
        return new WeatherRepository(weatherNetworkDataSource, forecastDao);
    }

    @Provides
    @MainActivityScope
    public DeleteLocationItem provideDeleteLocationItem(WeatherViewModel weatherViewModel, LocationAdapter locationAdapter) {
        return new DeleteLocationItemOnSwipe(weatherViewModel, locationAdapter);
    }

    @Provides
    @MainActivityScope
    public LocationAdapter locationAdapter(){
        return new LocationAdapter();
    }

    @MainActivityScope
    @Provides
    public LocationProvider provideLocation(){
        return new LocationProviderImpl();
    }
}
