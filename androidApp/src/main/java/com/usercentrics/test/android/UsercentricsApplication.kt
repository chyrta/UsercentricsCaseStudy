package com.usercentrics.test.android

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.usercentrics.test.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class UsercentricsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger(if (isDebug()) Level.ERROR else Level.NONE)
            androidContext(this@UsercentricsApplication)
        }
    }

}

fun Context.isDebug() = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE