package com.usercentrics.test.di

import com.usercentrics.sdk.Usercentrics
import com.usercentrics.test.base.executor.MainDispatcher
import com.usercentrics.test.features.costCalculator.presentation.CostCalculatorViewModel
import com.usercentrics.test.sdk.UsercentricsAndroidNative
import com.usercentrics.test.sdk.UsercentricsProxy
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { MainDispatcher() }

    single<UsercentricsProxy> {
        val userCentrics = UsercentricsAndroidNative(androidApplication(), Usercentrics)
        userCentrics.initialize("gChmbFIdL")
        userCentrics
    }

    single { CostCalculatorViewModel() }
}