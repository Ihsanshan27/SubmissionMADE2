package com.dicoding.submissionmade1

import android.app.Application
import com.dicoding.submissionmade1.core.di.databaseModule
import com.dicoding.submissionmade1.core.di.networkModule
import com.dicoding.submissionmade1.core.di.repositoryModule
import com.dicoding.submissionmade1.core.di.settingsModule
import com.dicoding.submissionmade1.di.useCaseModule
import com.dicoding.submissionmade1.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    settingsModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}