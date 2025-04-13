package com.example.booktrackapplication

import android.app.Application
import com.example.booktrackapplication.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BookTrackApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BookTrackApplication)
            modules(appModule)
        }
    }

}