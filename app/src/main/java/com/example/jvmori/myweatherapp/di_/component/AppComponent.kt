package com.example.jvmori.myweatherapp.di_.component

import android.app.Application
import com.example.jvmori.myweatherapp.application.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [AndroidSupportInjectionModule::class]
)
interface AppComponent : AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder{
        fun build() : AppComponent
        @BindsInstance
        fun application(application: Application) : Builder
    }
}