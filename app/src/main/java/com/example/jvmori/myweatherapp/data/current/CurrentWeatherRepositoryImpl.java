package com.example.jvmori.myweatherapp.data.current;

import com.example.jvmori.myweatherapp.data.NetworkBoundResource;
import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.data.network.Api;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CurrentWeatherRepositoryImpl implements CurrentWeatherRepository {

    private Api api;
    private CurrentWeatherDao dao;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public CurrentWeatherRepositoryImpl(Api api, CurrentWeatherDao dao) {
        this.api = api;
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
            protected void saveCallResult(CurrentWeatherResponse item) {
                Completable.fromAction(() ->
                        dao.insert(item))
                        .subscribeOn(Schedulers.io())
                        .subscribe();
            }

            @Override
            protected CurrentWeatherUI mapper(CurrentWeatherResponse currentWeatherResponse) {
                return dataMapper(currentWeatherResponse);
            }


            @Override
            protected Flowable<CurrentWeatherResponse> getLocal() {
                return dao.getCurrentWeatherByCity(city)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io());
            }

            @Override
            protected Single<CurrentWeatherResponse> getRemote() {
                return api.getCurrentWeatherByCity(city)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Maybe<CurrentWeatherResponse> getCurrentWeatherByGeographic(String latitude, String longitude) {
        return null;
    }

    private CurrentWeatherUI dataMapper(CurrentWeatherResponse response) {
        if (response != null) {
            return new CurrentWeatherUI(
                    response.getDt(),
                    response.getName(),
                    response.getSys().getCountry(),
                    response.getWeather().get(0).getMain(),
                    response.getWeather().get(0).getDescription(),
                    response.getWeather().get(0).getIcon(),
                    response.getMain().getTemp().toString(),
                    response.getMain().getPressure().toString(),
                    response.getMain().getHumidity().toString(),
                    response.getMain().getTempMin().toString(),
                    response.getMain().getTempMin().toString(),
                    response.getWind().getDeg().toString(),
                    response.getWind().getSpeed().toString()
            );
        }
        return null;
    }
}
