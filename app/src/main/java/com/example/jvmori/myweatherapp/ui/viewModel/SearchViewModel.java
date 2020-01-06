package com.example.jvmori.myweatherapp.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.ui.Resource;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<Resource<List<Search>>> _cities = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    public LiveData<Resource<List<Search>>> cities() {return _cities;}
    final PublishSubject<String> subject = PublishSubject.create();

    public void subjectOnNext(String query){
        subject.onNext(query);
    }



    @Override
    protected void onCleared() {
        disposable.clear();
    }
}
