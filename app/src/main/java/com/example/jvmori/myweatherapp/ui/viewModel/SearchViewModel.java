package com.example.jvmori.myweatherapp.ui.viewModel;

import android.app.Application;

import com.example.jvmori.myweatherapp.application.WeatherApplication;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SearchViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        WeatherApplication weatherApplication = (WeatherApplication) application;
        weatherRepository = weatherApplication.weatherRepository();
    }

    public LiveData<List<Search>> getResultsForCity(String city){ return weatherRepository.getResultsForCity(city);}
}
