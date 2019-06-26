package com.example.jvmori.myweatherapp.ui.viewModel;

import android.util.Log;

import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.ui.Resource;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.WeatherParameters;
import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends ViewModel {
    private MutableLiveData<Resource<ForecastEntry>> _weather = new MutableLiveData<>();

    public LiveData<Resource<ForecastEntry>> getWeather() {
        return _weather;
    }

    private MutableLiveData<List<ForecastEntry>> _allWeatherFromDb = new MutableLiveData<>();

    public LiveData<List<ForecastEntry>> allWeatherFromDb() {
        return _allWeatherFromDb;
    }

    private WeatherRepository weatherRepository;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public WeatherViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void fetchWeather(WeatherParameters weatherParameters) {
        _weather.setValue(Resource.loading(null));
        disposable.add(
                weatherRepository.getWeatherLocal(weatherParameters.getLocation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(success -> {
                                    if (success != null) {
                                        _weather.setValue(Resource.success(success));
                                        checkIfRefreshNeeded(success, weatherParameters.isDeviceLocation(), weatherParameters.getDays());
                                    }
                                }, error -> {
                                    _weather.setValue(Resource.error(error.getMessage(), null));
                                    Log.i("WEATHER", "Something went wrong!");
                                }, () -> {
                                    fetchRemote(weatherParameters);
                                }
                        )
        );
    }

    private void fetchRemote(WeatherParameters weatherParameters) {
        _weather.setValue(Resource.loading(null));
        disposable.add(
                weatherRepository.getWeatherRemote(weatherParameters)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doAfterSuccess(succes -> {
                            succes.getLocation().mCityName = weatherParameters.getCityName();
                            weatherRepository.persistWeather(succes);
                        })
                        .subscribe(
                                success -> {
                                    success.getLocation().mCityName = weatherParameters.getCityName();
                                    _weather.setValue(Resource.success(success));
                                }, error -> {
                                    _weather.setValue(Resource.error(error.getMessage(), null));
                                    Log.i("WEATHER", "Something went wrong!");
                                }
                        )
        );
    }

    private void checkIfRefreshNeeded(ForecastEntry oldWeather, boolean isDeviceLocation, String days) {
        if (!isWeatherUpToDate(oldWeather)) {
            fetchRemote(new WeatherParameters(
                    oldWeather.getLocation().mCityName,
                    oldWeather.getLocation().mCityName,
                    isDeviceLocation,
                    days
            ));
        }
    }

    private boolean isWeatherUpToDate(ForecastEntry oldWeather) {
        return oldWeather.getTimestamp() != 0L && System.currentTimeMillis() - oldWeather.getTimestamp() < Const.STALE_MS;
    }

    public void allForecastsFromDb() {
        disposable.add(
                weatherRepository.getAllForecast()
                        .subscribe(
                                next -> {
                                    _allWeatherFromDb.postValue(next);
                                },
                                error -> {
                                    Log.i("Error", "error");
                                }
                        )
        );
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
