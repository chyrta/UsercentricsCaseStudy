package com.usercentrics.test.di

import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.sdk.UsercentricsSDK
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel
import com.usercentrics.test.base.executor.MainDispatcher
import com.usercentrics.test.sdk.UsercentricsAndroidNative
import com.usercentrics.test.sdk.UsercentricsProxy
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { MainDispatcher() }

    single<UsercentricsProxy> {
        val userCentrics = UsercentricsAndroidNative(androidContext(), Usercentrics)
        userCentrics.initialize("gChmbFIdL")
        userCentrics
    }
}