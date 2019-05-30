package com.example.jvmori.myweatherapp.ui.viewModel;

import android.app.Application;
import android.util.Log;

import com.example.jvmori.myweatherapp.AppExecutors;
import com.example.jvmori.myweatherapp.application.WeatherApplication;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.WeatherParameters;
import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends AndroidViewModel {
    private MutableLiveData<ForecastEntry> _weather;
    public LiveData<ForecastEntry> getWeather() { return _weather;}

    private WeatherRepository weatherRepository;
    private MutableLiveData<ForecastEntry> weather = new MutableLiveData<>();
    private MutableLiveData<List<ForecastEntry>> allWeatherFromDb = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<ForecastEntry> updatedWeather = new MutableLiveData<>();

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        weatherRepository = ((WeatherApplication) application).weatherRepository();
    }

    public void fetchWeather(WeatherParameters weatherParameters) {
        _weather = new MutableLiveData<>();
        disposable.add(
                weatherRepository.getWeatherLocal(weatherParameters.getLocation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .doAfterSuccess(succes ->{
                            //TODO: save in db
                            Log.i("WEATHER", "success");
                        })
                        .subscribe(success -> {
                                    if (success != null) {
                                        _weather.setValue(success);
                                        checkIfRefreshNeeded(success, weatherParameters.isDeviceLocation(), weatherParameters.getDays());
                                    } else {
                                        fetchRemote(
                                                weatherParameters.getLocation(),
                                                weatherParameters.isDeviceLocation(),
                                                weatherParameters.getDays()
                                        );
                                    }
                                }, error -> {
                                    Log.i("WEATHER", "Something went wrong!");
                                }
                        )
        );
    }

    private void fetchRemote(String location, boolean isDeviceLocation, String days) {
        disposable.add(
                weatherRepository.getWeatherRemote(location, isDeviceLocation, days)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                success -> {
                                    _weather.setValue(success);
                                }, error -> {
                                    Log.i("WEATHER", "Something went wrong!");
                                }
                        )
        );
    }

    private void checkIfRefreshNeeded(ForecastEntry oldWeather, boolean isDeviceLocation, String days) {
        if (!isWeatherUpToDate(oldWeather)) {
            fetchRemote(oldWeather.getLocation().mCityName, isDeviceLocation, days);
        }
    }

    private boolean isWeatherUpToDate(ForecastEntry oldWeather) {
        return oldWeather.getTimestamp() != 0L && System.currentTimeMillis() - oldWeather.getTimestamp() < Const.STALE_MS;
    }


    public LiveData<ForecastEntry> getFreshWeather() {
        return updatedWeather;
    }

//    public LiveData<ForecastEntry> getWeather() {
//        return weather;
//    }

    public LiveData<List<ForecastEntry>> getAllWeather() {
        return allWeatherFromDb;
    }

    public void refreshWeather(ForecastEntry oldWeather) {
        if (oldWeather != null && !weatherRepository.isUpToDate(oldWeather.getTimestamp())) {
            disposable.add(
                    weatherRepository.getWeatherRemote(
                            oldWeather.getLocation().mCityName,
                            oldWeather.isDeviceLocation,
                            Const.FORECAST_DAYS)
                            .observeOn(Schedulers.io())
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    result -> updatedWeather.postValue(result),
                                    error -> {
                                        Log.i("WEATHER", "error");
                                    }
                            )
            );
        }
    }

    public Observable<ForecastEntry> getWeather(WeatherParameters weatherParameters) {
        return weatherRepository.getWeather(weatherParameters.getLocation(),
                weatherParameters.isDeviceLocation(),
                weatherParameters.getDays())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }


    public void fetchWeather(WeatherParameters weatherParameters, OnFailure onFailure) {
        disposable.add(
                weatherRepository.getWeather(
                        weatherParameters.getLocation(),
                        weatherParameters.isDeviceLocation(),
                        weatherParameters.getDays())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                success -> weather.postValue(success),
                                error -> {
                                    if (onFailure != null)
                                        onFailure.callback("Something went wrong!");
                                }
                        )
        );
    }

    public void allForecastsFromDb() {
        disposable.add(
                weatherRepository.getAllForecast()
                        .subscribe(
                                next -> {
                                    allWeatherFromDb.postValue(next);
                                },
                                error -> {
                                    Log.i("Error", "error");
                                },
                                () -> Log.i("Error", "error")

                        )
        );
    }

    public interface OnFailure {
        void callback(String message);
    }

    public void deleteWeather(String location) {
        weatherRepository.deleteWeather(location);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
