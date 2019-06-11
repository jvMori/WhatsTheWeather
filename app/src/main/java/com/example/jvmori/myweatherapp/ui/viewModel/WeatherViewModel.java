package com.example.jvmori.myweatherapp.ui.viewModel;

import android.util.Log;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
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
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends ViewModel {
    private MutableLiveData<ForecastEntry> _weather =  new MutableLiveData<>();
    public LiveData<ForecastEntry> getWeather() { return _weather;}
    private MutableLiveData<List<ForecastEntry>> _allWeatherFromDb = new MutableLiveData<>();
    public LiveData<List<ForecastEntry>> allWeatherFromDb() {return _allWeatherFromDb;}

    private WeatherRepository weatherRepository;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public WeatherViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void fetchWeather(WeatherParameters weatherParameters) {
        disposable.add(
                weatherRepository.getWeatherLocal(weatherParameters.getLocation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(success -> {
                                    if (success != null) {
                                        _weather.setValue(success);
                                        checkIfRefreshNeeded(success, weatherParameters.isDeviceLocation(), weatherParameters.getDays());
                                    } else {
                                        fetchRemote(weatherParameters);
                                    }
                                }, error -> {
                                    Log.i("WEATHER", "Something went wrong!");
                                }
                        )
        );
    }

    public void fetchRemote(WeatherParameters weatherParameters) {
        disposable.add(
                weatherRepository.getWeatherRemote(weatherParameters)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doAfterSuccess(succes ->{
                           weatherRepository.persistWeather(succes);
                        })
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
            fetchRemote(new WeatherParameters(
                    oldWeather.getLocation().mCityName,
                    isDeviceLocation,
                    days
            ));
        }
    }

    private boolean isWeatherUpToDate(ForecastEntry oldWeather) {
        return oldWeather.getTimestamp() != 0L && System.currentTimeMillis() - oldWeather.getTimestamp() < Const.STALE_MS;
    }

    public LiveData<List<ForecastEntry>> getAllWeather() {
        return _allWeatherFromDb;
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
