package com.example.jvmori.myweatherapp.di.modules.main;

import com.example.jvmori.myweatherapp.di.scope.MainActivityScope;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.DeleteLocationItem;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.DeleteLocationItemOnSwipe;
import com.example.jvmori.myweatherapp.ui.view.adapters.location.LocationAdapter;
import com.example.jvmori.myweatherapp.ui.viewModel.WeatherViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class WeatherFragmentModule {

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
}
