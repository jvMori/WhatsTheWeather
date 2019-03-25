package com.example.jvmori.myweatherapp.data.repository;

import com.example.jvmori.myweatherapp.AppExecutors;
import com.example.jvmori.myweatherapp.data.db.ForecastDao;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.WeatherParameters;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherRepository {

    private ForecastDao forecastDao;
    private WeatherNetworkDataSource weatherNetworkDataSource;
    private AppExecutors executors;

    @Inject
    public WeatherRepository(WeatherNetworkDataSource weatherNetworkDataSource, ForecastDao forecastDao) {
        this.weatherNetworkDataSource = weatherNetworkDataSource;
        this.forecastDao = forecastDao;
    }

    public Observable<ForecastEntry> getWeather(String location, boolean isDeviceLoc, String days) {
        return Maybe.concat(getWeatherFromDb(location), getWeatherRemote(location, isDeviceLoc, days))
                .filter(it -> isUpToDate(it.getTimestamp()))
                .take(1)
                .toObservable();
    }

    private Maybe<ForecastEntry> getWeatherFromDb(String location) {
        return forecastDao.getWeather(location)
                .subscribeOn(Schedulers.io());
    }

    private Maybe<ForecastEntry> getWeatherRemote(String location, boolean isDeviceLoc, String days) {
        return weatherNetworkDataSource.fetchWeather(
                new WeatherParameters(location, isDeviceLoc, days))
                .subscribeOn(Schedulers.io())
                .doOnSuccess(
                        it -> {
                            it.isDeviceLocation = isDeviceLoc;
                            persistForecast(it);
                        }
                );
    }

    public Observable<List<ForecastEntry>> getAllForecast() {
        return forecastDao.getAllWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private void persistForecast(ForecastEntry newForecastEntry) {
        newForecastEntry.setTimestamp(System.currentTimeMillis());
//TODO: delete old device location
        forecastDao.insert(newForecastEntry);
    }

    public void deleteWeather(String location) {
       // executors.diskIO().execute(() -> {
            forecastDao.deleteForecast(location);
       // });
    }

    public LiveData<List<Search>> getResultsForCity(String cityName) {
        return weatherNetworkDataSource.searchCity(cityName);
    }

    private boolean isUpToDate(Long lastUpdate) {
        return System.currentTimeMillis() - lastUpdate < Const.STALE_MS;
    }
}
