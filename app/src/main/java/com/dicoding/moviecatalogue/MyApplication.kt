package com.dicoding.moviecatalogue

import android.app.Application
import com.dicoding.moviecatalogue.core.di.*
import com.dicoding.moviecatalogue.di.useCaseModule
import com.dicoding.moviecatalogue.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(
                databaseModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
            ))
        }
    }
}