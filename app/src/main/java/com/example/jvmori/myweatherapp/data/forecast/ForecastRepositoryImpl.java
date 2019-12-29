package com.example.jvmori.myweatherapp.data.forecast;

import com.example.jvmori.myweatherapp.data.NetworkBoundResource;
import com.example.jvmori.myweatherapp.data.forecast.response.ForecastResponse;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.ForecastMapper;

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
    private CompositeDisposable disposable  = new CompositeDisposable();

    @Inject
    public ForecastRepositoryImpl(ForecastNetworkDataSource networkDataSource, ForecastDao localDataSource) {
        this.networkDataSource = networkDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public Flowable<Forecasts> getForecast(String location) {
        return Flowable.create(emitter -> new NetworkBoundResource<Forecasts, ForecastResponse>(emitter, disposable ){

            @Override
            protected boolean needRefresh(Forecasts data) {
                return System.currentTimeMillis() - data.getTimestamp() > Const.STALE_MS;
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
                Completable.fromAction(() ->
                        localDataSource.insert(data)
                ).subscribeOn(Schedulers.io()).subscribe();
            }

            @Override
            protected Forecasts mapper(ForecastResponse data) {
                return new Forecasts(
                        ForecastMapper.mapForecasts(data.getForecast()),
                        data.getCity().getCityName(),
                        System.currentTimeMillis()
                );
            }

            @Override
            protected void handleError(Throwable error) {
                emitter.onError(error);
            }
        }, BackpressureStrategy.BUFFER);
    }
}
