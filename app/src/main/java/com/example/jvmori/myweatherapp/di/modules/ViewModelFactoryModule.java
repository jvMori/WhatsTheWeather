package com.example.jvmori.myweatherapp.di.modules;
import androidx.lifecycle.ViewModelProvider;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;


import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);
}
