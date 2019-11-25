package com.example.jvmori.myweatherapp.data;

import io.reactivex.Flowable;
import io.reactivex.Maybe;


public abstract class NetworkBoundResource<ResultType, RequestType> {

    private Flowable<ResultType> result;

    protected NetworkBoundResource() {
        result = Maybe.concat(loadFromDb(), fetchFromNetwork())
                .filter(data1 ->
                        isDataFresh(data1))
                .take(1)
                .flatMap(response ->
                        Flowable.just(saveCallResult(response)))
                .flatMap(data -> Flowable.just(mapData(data)));
    }


    protected abstract RequestType saveCallResult(RequestType item);

    protected abstract ResultType mapData(RequestType requestType);

    protected abstract boolean isDataFresh(RequestType data);

    protected abstract Maybe<RequestType> loadFromDb();

    protected abstract Maybe<RequestType> fetchFromNetwork();

    public Flowable<ResultType> getResult() {
        return result;
    }
}
