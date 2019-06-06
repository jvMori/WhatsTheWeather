package com.example.jvmori.myweatherapp.di_.component

import android.app.Application
import com.example.jvmori.myweatherapp.application.BaseApplication
import com.example.jvmori.myweatherapp.di_.modules.ActivityBuildersModule
import com.example.jvmori.myweatherapp.di_.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [AndroidSupportInjectionModule::class, ActivityBuildersModule::class, AppModule::class]
)
interface AppComponent : AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder{
        fun build() : AppComponent
        @BindsInstance
        fun application(application: Application) : Builder
    }
}