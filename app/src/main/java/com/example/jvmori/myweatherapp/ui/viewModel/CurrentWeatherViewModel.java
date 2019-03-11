package com.example.jvmori.myweatherapp.ui.viewModel;

import android.app.Application;

import com.example.jvmori.myweatherapp.AppExecutors;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.util.WeatherParameters;
import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CurrentWeatherViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;

    public CurrentWeatherViewModel(@NonNull  Application application) {
        super(application);
        weatherRepository = WeatherRepository.getInstance(application, AppExecutors.getInstance());
    }

    public LiveData<ForecastEntry> downloadWeather(WeatherParameters parameters,WeatherRepository.OnFailure onFailure){
        return weatherRepository.downloadWeather(parameters, onFailure);
    }

    public LiveData<List<ForecastEntry>> getAllForecast(){ return  weatherRepository.getAllForecast();}

    public LiveData<List<ForecastEntry>> allForecastsWithoutLoc() {return  weatherRepository.allForecastsWithoutLoc();}

}
