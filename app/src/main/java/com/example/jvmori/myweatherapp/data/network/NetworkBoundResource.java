package com.example.jvmori.myweatherapp.data.network;

import io.reactivex.FlowableEmitter;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public abstract class NetworkBoundResource<ResultType, RequestType> {
    protected NetworkBoundResource(FlowableEmitter<ResultType> emitter, CompositeDisposable disposable) {
        CompositeDisposable localDisposable = new CompositeDisposable();
        localDisposable.add(
                getLocal()
                        .doOnComplete(() -> //when there is no such data in db
                        {
                            fetchFromNetwork(emitter, disposable, localDisposable);
                        })
                        .map (localData -> {
                            if (needRefresh(localData)){
                                fetchFromNetwork(emitter, disposable, localDisposable);
                            }
                            return localData;
                        })
                        .subscribe(
                                emitter::onNext
                        )
        );

    }

    private void fetchFromNetwork(FlowableEmitter<ResultType> emitter, CompositeDisposable disposable, CompositeDisposable localDisposable) {
        disposable.add(
                getRemote().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(this::mapper)
                        .subscribe(data -> {
                            localDisposable.dispose();
                            saveCallResult(data);
                            disposable.add(
                                    getLocal()
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(emitter::onNext)
                            );
                        }, this::handleError)
        );
    }

    protected abstract boolean needRefresh(ResultType data);

    protected abstract Single<RequestType> getRemote();

    protected abstract Maybe<ResultType> getLocal();

    protected abstract void saveCallResult(ResultType data);

    protected abstract ResultType mapper(RequestType data);

    protected abstract void handleError(Throwable error);

}
