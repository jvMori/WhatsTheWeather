package com.example.jvmori.myweatherapp.ui.viewModel;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class SearchViewModel extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCleared() {
        disposable.clear();
    }
}
