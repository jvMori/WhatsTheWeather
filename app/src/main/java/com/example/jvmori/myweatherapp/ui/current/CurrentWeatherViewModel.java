package com.example.jvmori.myweatherapp.ui.current;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jvmori.myweatherapp.data.current.CurrentWeatherRepository;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherUI;
import com.example.jvmori.myweatherapp.ui.Resource;

import javax.inject.Inject;

public class CurrentWeatherViewModel extends ViewModel {

    private CurrentWeatherRepository repository;

    public LiveData<Resource<CurrentWeatherUI>> getCurrentWeather(String cityName) {
        return repository.getCurrentWeatherByCity(cityName);
    }

    @Inject
    public CurrentWeatherViewModel(CurrentWeatherRepository repository){
        this.repository = repository;
    }

}
