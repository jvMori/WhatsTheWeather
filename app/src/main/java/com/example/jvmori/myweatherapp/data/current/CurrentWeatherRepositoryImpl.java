package com.example.jvmori.myweatherapp.data.current;

import android.location.Location;
import android.util.Log;

import com.example.jvmori.myweatherapp.data.network.NetworkBoundResource;
import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.RoundUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
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
    public Observable<List<CurrentWeatherUI>> getAllWeather() {
        return dao.getAllWeather().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).toObservable();
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
            protected Maybe<CurrentWeatherUI> getLocal() {
                return dao.getCurrentWeatherByCity(city)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io());
            }

            @Override
            protected boolean needRefresh(CurrentWeatherUI data) {
                return System.currentTimeMillis() - data.getTimestamp() > Const.STALE_MS;
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
        return Flowable.create(emitter -> {
            new NetworkBoundResource<CurrentWeatherUI, CurrentWeatherResponse>(emitter, disposable) {

                @Override
                protected boolean needRefresh(CurrentWeatherUI data) {
                    return System.currentTimeMillis() - data.getTimestamp() > Const.STALE_MS;
                }

                @Override
                protected Single<CurrentWeatherResponse> getRemote() {
                    return networkDataSource.getCurrentWeatherByGeographic(location);
                }

                @Override
                protected Maybe<CurrentWeatherUI> getLocal() {
                    String lon = Double.toString(RoundUtil.round(location.getLongitude(), 2));
                    String lat = Double.toString(RoundUtil.round(location.getLatitude(), 2));
                    return dao.getCurrentWeatherByGeographic(lon, lat)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .doOnComplete(() ->
                                Log.i("WEATHER", "completed! no such data in db"));
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
            };
        }, BackpressureStrategy.LATEST);
    }

    private CurrentWeatherUI dataMapper(CurrentWeatherResponse response) {
        if (response != null) {
            String lon = Double.toString(RoundUtil.round(response.getCoord().getLon(), 2));
            String lat = Double.toString(RoundUtil.round(response.getCoord().getLat(), 2));
            return new CurrentWeatherUI(
                    System.currentTimeMillis(),
                    response.getName(),
                    lon,
                    lat,
                    response.getSys().getCountry(),
                    response.getWeather().get(0).getMain(),
                    response.getWeather().get(0).getDescription(),
                    response.getWeather().get(0).getIcon(),
                    response.getMain().getTemp(),
                    response.getMain().getPressure().toString(),
                    response.getMain().getHumidity().toString(),
                    response.getClouds().getAll().toString(),
                    response.getWind().getSpeed().toString()
            );
        }
        return null;
    }
}
