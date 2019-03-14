package com.example.jvmori.myweatherapp.ui.viewModel;

import android.app.Application;
import android.util.Log;

import com.example.jvmori.myweatherapp.AppExecutors;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.util.WeatherParameters;
import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;
    private MutableLiveData<ForecastEntry> weather = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        weatherRepository = WeatherRepository.getInstance(application, AppExecutors.getInstance());
    }

    public void fetchWeather(WeatherParameters weatherParameters) {
        disposable.add(
                weatherRepository.getWeather(
                        weatherParameters.getLocation(),
                        weatherParameters.isDeviceLocation(),
                        weatherParameters.getDays())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                success -> weather.postValue(success),
                                error -> Log.i("Error", "Something went wrong!")
                        )
        );
    }
    public LiveData<ForecastEntry> getWeather(){return weather;}

    public void deleteWeather(String location) {
        weatherRepository.deleteWeather(location);
    }

    public LiveData<List<ForecastEntry>> getAllForecast() {
        return weatherRepository.getAllForecast();
    }

    public LiveData<List<ForecastEntry>> allForecastsWithoutLoc() {
        return weatherRepository.allForecastsWithoutLoc();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
