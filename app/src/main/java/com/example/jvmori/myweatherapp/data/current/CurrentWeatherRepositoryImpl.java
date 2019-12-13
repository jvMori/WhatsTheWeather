package com.example.jvmori.myweatherapp.data.current;

import android.location.Location;

import com.example.jvmori.myweatherapp.data.NetworkBoundResource;
import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CurrentWeatherRepositoryImpl implements CurrentWeatherRepository {

    private CurrentWeatherNetworkDataSource networkDataSource;
    private CurrentWeatherDao dao;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public CurrentWeatherRepositoryImpl(CurrentWeatherNetworkDataSource networkDataSource, CurrentWeatherDao dao) {
        this.networkDataSource = networkDataSource;
        this.dao = dao;
    }

    @Override
    public void cleanup() {
        disposable.clear();
    }

    @Override
    public Flowable<CurrentWeatherUI> getCurrentWeatherByCity(String city) {
        return Flowable.create(emitter -> new NetworkBoundResource<CurrentWeatherUI, CurrentWeatherResponse>(emitter, disposable) {

            @Override
            protected void saveCallResult(CurrentWeatherUI item) {
                saveInDb(item);
            }

            @Override
            protected CurrentWeatherUI mapper(CurrentWeatherResponse currentWeatherResponse) {
                return dataMapper(currentWeatherResponse);
            }


            @Override
            protected Flowable<CurrentWeatherUI> getLocal() {
                return dao.getCurrentWeatherByCity(city)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io());
            }

            @Override
            protected Single<CurrentWeatherResponse> getRemote() {
                return networkDataSource.getCurrentWeatherByCity(city);
            }

            @Override
            protected void handleError(Throwable error) {
                emitter.onError(error);
            }
        }, BackpressureStrategy.BUFFER);
    }

    private void saveInDb(CurrentWeatherUI item) {
        Completable.fromAction(() ->
                dao.insert(item))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public Flowable<CurrentWeatherUI> getCurrentWeatherByGeographic(Location location) {
        return Flowable.create(emitter -> new NetworkBoundResource<CurrentWeatherUI, CurrentWeatherResponse>(emitter, disposable){

                    @Override
                    protected Single<CurrentWeatherResponse> getRemote() {
                        return networkDataSource.getCurrentWeatherByGeographic(location).firstOrError();
                    }

                    @Override
                    protected Flowable<CurrentWeatherUI> getLocal() {
                        return dao.getCurrentWeatherByGeographic(location.getLongitude(), location.getLatitude())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io());
                    }

                    @Override
                    protected void saveCallResult(CurrentWeatherUI data) {
                        saveInDb(data);
                    }

                    @Override
                    protected CurrentWeatherUI mapper(CurrentWeatherResponse data) {
                        return dataMapper(data);
                    }

                    @Override
                    protected void handleError(Throwable error) {
                        emitter.onError(error);
                    }
                },
                BackpressureStrategy.BUFFER);
    }

    private CurrentWeatherUI dataMapper(CurrentWeatherResponse response) {
        if (response != null) {
            return new CurrentWeatherUI(
                    System.currentTimeMillis(),
                    response.getName(),
                    response.getCoord().getLon(),
                    response.getCoord().getLat(),
                    response.getSys().getCountry(),
                    response.getWeather().get(0).getMain(),
                    response.getWeather().get(0).getDescription(),
                    response.getWeather().get(0).getIcon(),
                    response.getMain().getTemp().toString(),
                    response.getMain().getPressure().toString(),
                    response.getMain().getHumidity().toString(),
                    response.getClouds().getAll().toString(),
                    response.getWind().getSpeed().toString()
            );
        }
        return null;
    }
}
