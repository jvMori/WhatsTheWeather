package com.example.jvmori.myweatherapp.di.modules.main;
import androidx.lifecycle.ViewModelProvider;
import com.example.jvmori.myweatherapp.di.scope.MainActivityScope;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;


import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {
    @Binds
    @MainActivityScope
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);
}
