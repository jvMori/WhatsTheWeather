package com.example.jvmori.myweatherapp.data.forecast;

import android.location.Location;

import com.example.jvmori.myweatherapp.data.network.NetworkBoundResource;
import com.example.jvmori.myweatherapp.data.forecast.response.ForecastResponse;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.ForecastMapper;
import com.example.jvmori.myweatherapp.util.RoundUtil;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ForecastRepositoryImpl implements ForecastRepository {

    private ForecastNetworkDataSource networkDataSource;
    private ForecastDao localDataSource;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public ForecastRepositoryImpl(ForecastNetworkDataSource networkDataSource, ForecastDao localDataSource) {
        this.networkDataSource = networkDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public Flowable<Forecasts> getForecast(String location) {
        return Flowable.create(emitter -> new NetworkBoundResource<Forecasts, ForecastResponse>(emitter, disposable) {

            @Override
            protected boolean needRefresh(Forecasts data) {
                return shouldRefresh(data);
            }

            @Override
            protected Single<ForecastResponse> getRemote() {
                return networkDataSource.getForecast(location);
            }

            @Override
            protected Maybe<Forecasts> getLocal() {
                return localDataSource.getForecast(location);
            }

            @Override
            protected void saveCallResult(Forecasts data) {
                saveData(data);
            }

            @Override
            protected Forecasts mapper(ForecastResponse data) {
                return mapData(data, null);
            }

            @Override
            protected void handleError(Throwable error) {
                emitter.onError(error);
            }
        }, BackpressureStrategy.BUFFER);
    }

    private Forecasts mapData(ForecastResponse data, Location location) {
        if (data != null) {
            String longitude = "";
            String latitude = "";
            if (location != null) {
                longitude = Double.toString(RoundUtil.round(location.getLongitude(), 2));
                latitude = Double.toString(RoundUtil.round(location.getLatitude(), 2));
            }
            return new Forecasts(
                    ForecastMapper.mapForecasts(data.getForecast()),
                    data.getCity().getCityName(),
                    longitude,
                    latitude,
                    System.currentTimeMillis()
            );
        }
        return null;
    }

    private void saveData(Forecasts data) {
        Completable.fromAction(() ->
                localDataSource.insert(data)
        ).subscribeOn(Schedulers.io()).subscribe();
    }

    private boolean shouldRefresh(Forecasts data) {
        return System.currentTimeMillis() - data.getTimestamp() > Const.STALE_MS;
    }

    @Override
    public Flowable<Forecasts> getForecastByGeo(Location location) {
        return Flowable.create(emitter -> new NetworkBoundResource<Forecasts, ForecastResponse>(emitter, disposable) {

            @Override
            protected boolean needRefresh(Forecasts data) {
                return shouldRefresh(data);
            }

            @Override
            protected Single<ForecastResponse> getRemote() {
                return networkDataSource.getForecastByGeo(location);
            }

            @Override
            protected Maybe<Forecasts> getLocal() {
                String lon = Double.toString(RoundUtil.round(location.getLongitude(), 2));
                String lat = Double.toString(RoundUtil.round(location.getLatitude(), 2));
                return localDataSource.getForecastByGeo(lat, lon);
            }

            @Override
            protected void saveCallResult(Forecasts data) {
                saveData(data);
            }

            @Override
            protected Forecasts mapper(ForecastResponse data) {
                return mapData(data, location);
            }

            @Override
            protected void handleError(Throwable error) {
                emitter.onError(error);
            }
        }, BackpressureStrategy.BUFFER);
    }
}
