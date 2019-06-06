package com.example.jvmori.myweatherapp.di.modules;
import androidx.lifecycle.ViewModelProvider;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;


import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {
    @Binds
    @Singleton
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);
}
