package com.example.jvmori.myweatherapp.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.jvmori.myweatherapp.ui.Resource;


public abstract class NetworkBoundResource<ResultType, RequestType> {

    private MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    public NetworkBoundResource() {
        result.setValue(Resource.loading(null));
        //LiveData<ResultType> dbSource = ;
        result.addSource(loadFromDb(), new Observer<ResultType>() {
            @Override
            public void onChanged(ResultType resultType) {
                result.removeSource(loadFromDb());
                if (shouldFetch(resultType)) {
                    fetchFromNetwork(loadFromDb());
                } else {
                    result.addSource(loadFromDb(), newData ->
                            result.setValue(Resource.success(newData)));
                }
            }
        });
    }

    private void fetchFromNetwork(LiveData<ResultType> dbSource) {
       LiveData<Resource<RequestType>> apiSource = createCall();
       result.addSource(dbSource, data -> {
           result.setValue(Resource.loading(data));
       });
       result.addSource(apiSource, response -> {
           result.removeSource(apiSource);
           result.removeSource(dbSource);
           if(response.status ==  Resource.Status.LOADING){
               result.setValue(Resource.loading(null));
           }
           else if (response.status == Resource.Status.SUCCESS){
               saveCallResult(response.data);
               result.addSource(loadFromDb(), newData -> {
                   result.setValue(Resource.success(newData));
               });
           }else if (response.status == Resource.Status.ERROR){
               onFetchFailed();
               result.addSource(dbSource, newData -> {
                   String message = response.message != null ? response.message : "No internet connection";
                   result.setValue(Resource.error(message, newData));
               });
           }
       });
    }

    protected abstract void saveCallResult(RequestType item);

    protected abstract boolean shouldFetch(ResultType data);

    protected abstract LiveData<ResultType> loadFromDb();

    protected abstract LiveData<Resource<RequestType>> createCall();

    protected abstract void onFetchFailed();

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }
}
