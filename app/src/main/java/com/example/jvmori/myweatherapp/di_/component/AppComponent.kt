package com.example.jvmori.myweatherapp.di_.component

import android.app.Application
import com.example.jvmori.myweatherapp.application.BaseApplication
import com.example.jvmori.myweatherapp.di_.modules.ActivityBuildersModule
import com.example.jvmori.myweatherapp.di_.modules.AppModule
import com.example.jvmori.myweatherapp.di_.modules.ViewModelFactoryModule
import com.example.jvmori.myweatherapp.di_.modules.app.DataSourceModule
import com.example.jvmori.myweatherapp.di_.modules.app.ImageLoaderModule
import com.example.jvmori.myweatherapp.di_.scope.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScope
@Component(
    modules = [AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        DataSourceModule::class,
        ImageLoaderModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder{
        fun build() : AppComponent
        @BindsInstance
        fun application(application: Application) : Builder
    }
}