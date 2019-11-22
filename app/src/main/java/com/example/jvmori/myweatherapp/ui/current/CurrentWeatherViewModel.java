package com.example.jvmori.myweatherapp.ui.current;

import androidx.lifecycle.ViewModel;

import com.example.jvmori.myweatherapp.data.current.CurrentWeatherRepository;

import javax.inject.Inject;

public class CurrentWeatherViewModel extends ViewModel {

    private CurrentWeatherRepository repository;

    @Inject
    public CurrentWeatherViewModel(CurrentWeatherRepository repository){
        this.repository = repository;
    }

}
