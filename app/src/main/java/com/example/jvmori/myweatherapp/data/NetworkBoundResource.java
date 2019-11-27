package com.example.jvmori.myweatherapp.data;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public abstract class NetworkBoundResource<ResultType, RequestType> {


    protected NetworkBoundResource(FlowableEmitter<ResultType> emitter,  CompositeDisposable disposable ) {
        CompositeDisposable localDisposable = new CompositeDisposable();
        localDisposable.add(
                getLocal()
                        .map(this::mapper)
                        .subscribe(emitter::onNext)
        );
        disposable.add(
                getRemote().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                            localDisposable.dispose();
                            saveCallResult(data);
                            disposable.add(
                                    getLocal().map(this::mapper).subscribe(emitter::onNext)
                            );
                        })
        );
    }

    protected abstract Single<RequestType> getRemote();

    protected abstract Flowable<RequestType> getLocal();

    protected abstract void saveCallResult(RequestType data);

    protected abstract ResultType mapper(RequestType data);
}
