package com.example.jvmori.myweatherapp.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.jvmori.myweatherapp.ui.Resource;

public abstract class NetworkBoundResource<ResultType, RequestType>
{
    private MediatorLiveData<Resource<ResultType>> result;

    public NetworkBoundResource() {
      result.setValue(Resource.loading(null));
    }

    protected abstract void saveCallResult(ResultType item);
    protected abstract boolean shouldFetch(ResultType data);
    protected abstract LiveData<ResultType> loadFromDb();
    protected abstract LiveData<Resource<RequestType>> createCall();
    protected abstract void onFetchFailed();
    protected LiveData<Resource<ResultType>> asLiveData(){
        return  result;
    }
}
