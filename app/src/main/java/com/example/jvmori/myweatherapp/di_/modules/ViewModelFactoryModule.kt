package com.example.jvmori.myweatherapp.di_.modules

import androidx.lifecycle.ViewModelProvider
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(modelProviderFactory: ViewModelProviderFactory) : ViewModelProvider.Factory
}