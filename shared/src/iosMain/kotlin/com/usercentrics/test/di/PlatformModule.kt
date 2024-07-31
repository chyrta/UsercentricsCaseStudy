package com.usercentrics.test.di

import com.usercentrics.test.base.executor.MainDispatcher
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { MainDispatcher() }
}