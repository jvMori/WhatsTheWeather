package com.example.jvmori.myweatherapp.di.modules.main;

import androidx.lifecycle.ViewModel;

import com.example.jvmori.myweatherapp.di.ViewModelKey;
import com.example.jvmori.myweatherapp.ui.viewModel.SearchViewModel;
import com.example.jvmori.myweatherapp.ui.viewModel.WeatherViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class WeatherViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel.class)
    public abstract ViewModel bindWeatherViewModel(WeatherViewModel weatherViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    public abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);
}
