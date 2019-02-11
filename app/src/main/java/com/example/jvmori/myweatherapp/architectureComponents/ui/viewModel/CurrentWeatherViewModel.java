package com.example.jvmori.myweatherapp.architectureComponents.ui.viewModel;

import android.app.Application;

import com.example.jvmori.myweatherapp.architectureComponents.AppExecutors;
import com.example.jvmori.myweatherapp.architectureComponents.data.util.WeatherParameters;
import com.example.jvmori.myweatherapp.architectureComponents.data.WeatherRepository;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeather;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class CurrentWeatherViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;
    private MutableLiveData<CurrentWeather> currentWeatherLiveData;
    private Application application;

    public CurrentWeatherViewModel(@NonNull  Application application) {
        super(application);
        this.application = application;
        weatherRepository = WeatherRepository.getInstance(application, AppExecutors.getInstance());
        currentWeatherLiveData = new MutableLiveData<>();
    }
    public LiveData<CurrentWeather> getCurrentWeather(WeatherParameters weatherParameters, WeatherRepository.OnFailure onFailure)  {
        return weatherRepository.initWeatherData(weatherParameters, onFailure);
    }

    public LiveData<List<CurrentWeather>> getAllWeather(){
        return weatherRepository.getAllWeather();
    }

}
