package com.example.jvmori.myweatherapp.di.modules.main;

import androidx.lifecycle.ViewModel;

import com.example.jvmori.myweatherapp.di.ViewModelKey;
import com.example.jvmori.myweatherapp.ui.current.CurrentWeatherViewModel;
import com.example.jvmori.myweatherapp.ui.forecast.ForecastViewModel;
import com.example.jvmori.myweatherapp.ui.viewModel.LocationViewModel;
import com.example.jvmori.myweatherapp.ui.viewModel.SearchViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class WeatherViewModelsModule {

//    @Binds
//    @IntoMap
//    @ViewModelKey(SearchViewModel.class)
//    public abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel.class)
    public abstract ViewModel bindLocationViewModel(LocationViewModel locationViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CurrentWeatherViewModel.class)
    public abstract ViewModel bindCurrentWeatherViewModel(CurrentWeatherViewModel currentWeatherViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ForecastViewModel.class)
    public abstract ViewModel bindForecastViewModel(ForecastViewModel currentWeatherViewModel);
}
