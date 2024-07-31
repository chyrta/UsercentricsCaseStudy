package com.usercentrics.test.android.di;

import com.usercentrics.test.features.costCalculator.CostCalculatorViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { CostCalculatorViewModel() }
}
