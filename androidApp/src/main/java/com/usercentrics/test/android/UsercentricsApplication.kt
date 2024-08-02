package com.usercentrics.test.android

import android.app.Application
import com.usercentrics.test.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class UsercentricsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin({
            androidLogger(Level.ERROR)
            androidContext(this@UsercentricsApplication)
        })
    }

}