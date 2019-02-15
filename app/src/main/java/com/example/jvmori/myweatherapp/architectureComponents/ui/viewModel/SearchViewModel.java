package com.example.jvmori.myweatherapp.architectureComponents.ui.viewModel;

import android.app.Application;

import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.SearchResponse;
import com.example.jvmori.myweatherapp.architectureComponents.data.repository.WeatherRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SearchViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        weatherRepository = WeatherRepository.getInstance(application, null);
    }

    public LiveData<SearchResponse> getResultsForCity(String city){ return weatherRepository.getResultsForCity(city);}
}
