package com.rickandmorty.kmp

import android.app.Application
import com.rickandmorty.kmp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.rickandmorty.kmp.BuildConfig

class RickAndMorty : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@RickAndMorty)
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.NONE)
        }
    }
}
