package com.example.jvmori.myweatherapp.data.repository;

import com.example.jvmori.myweatherapp.data.db.ForecastDao;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.WeatherParameters;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherRepository {

    private ForecastDao forecastDao;
    private WeatherNetworkDataSource weatherNetworkDataSource;

    @Inject
    public WeatherRepository(WeatherNetworkDataSource weatherNetworkDataSource, ForecastDao forecastDao) {
        this.weatherNetworkDataSource = weatherNetworkDataSource;
        this.forecastDao = forecastDao;
    }

    public Observable<ForecastEntry> getWeatherLocal(String location) {
        return forecastDao.getWeather(location)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Maybe<ForecastEntry> getWeatherRemote(WeatherParameters weatherParameters) {
        return weatherNetworkDataSource.fetchWeather(weatherParameters)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(
                        it -> {
                            it.isDeviceLocation = weatherParameters.isDeviceLocation();
                            it.getLocation().mCityName = weatherParameters.getLocation();
                            it.setTimestamp(System.currentTimeMillis());
                            if (weatherParameters.isDeviceLocation()){
                                insertAdnDeleteOldLocation(it);
                            }else{
                                persistWeather(it);
                            }
                        }
                );
    }

    public Observable<List<ForecastEntry>> getAllForecast() {
        return forecastDao.getAllWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    private void insertAdnDeleteOldLocation(ForecastEntry forecastEntry){
        Completable.fromAction(() ->
                    forecastDao.insertAndDeleteOldLocation(forecastEntry))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void persistWeather(ForecastEntry newForecastEntry) {
        newForecastEntry.setTimestamp(System.currentTimeMillis());
        Completable.fromAction(() -> forecastDao.updateWeather(newForecastEntry))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteWeather(String location) {
        Completable.fromAction(() -> forecastDao.deleteForecast(location))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    private void deleteOldDeviceLocation() {
        Completable.fromAction(()-> forecastDao.deleteOldDeviceLocWeather())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public Observable<List<Search>> getResultsForCity(String cityName) {
        return weatherNetworkDataSource.searchCity(cityName);
    }

    public boolean isUpToDate(Long lastUpdate) {
        return System.currentTimeMillis() - lastUpdate < Const.STALE_MS;
    }
}
