package com.example.jvmori.myweatherapp.architectureComponents.ui.viewModel;

import android.app.Application;

import com.example.jvmori.myweatherapp.architectureComponents.AppExecutors;
import com.example.jvmori.myweatherapp.architectureComponents.data.WeatherRepository;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeather;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;

import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class CurrentWeatherViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;
    private MediatorLiveData<CurrentWeather> currentWeatherLiveData;

    public CurrentWeatherViewModel(@NonNull Application application) {
        super(application);
        weatherRepository = WeatherRepository.getInstance(application, AppExecutors.getInstance());
        currentWeatherLiveData = new MediatorLiveData<>();
    }
    public LiveData<CurrentWeather> getCurrentWeather()  {
        currentWeatherLiveData.addSource(weatherRepository.initWeatherData(), new Observer<CurrentWeather>() {
            @Override
            public void onChanged(CurrentWeather currentWeather) {
                currentWeatherLiveData.postValue(currentWeather);
            }
        });
        return currentWeatherLiveData;
    }
}
