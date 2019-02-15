package com.example.jvmori.myweatherapp.architectureComponents.ui.viewModel;

import android.app.Application;

import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.Search;
import com.example.jvmori.myweatherapp.architectureComponents.data.repository.WeatherRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SearchViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        weatherRepository = WeatherRepository.getInstance(application, null);
    }

    public LiveData<List<Search>> getResultsForCity(String city){ return weatherRepository.getResultsForCity(city);}
}
