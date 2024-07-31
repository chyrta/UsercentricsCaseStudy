package com.usercentrics.test.di

import com.usercentrics.test.features.costCalculator.CalculateVirtualCostUsecase
import com.usercentrics.test.features.costCalculator.CostCalculatorProcessor
import com.usercentrics.test.features.costCalculator.CostCalculatorViewModel
import com.usercentrics.test.features.costCalculator.rule.BankingSnoopyRule
import com.usercentrics.test.features.costCalculator.rule.CostRule
import com.usercentrics.test.features.costCalculator.rule.GoodCitizenRule
import com.usercentrics.test.features.costCalculator.rule.WhyDoYouCareRule
import com.usercentrics.test.sdk.UsercentricsProxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            appModule,
            dispatcherModule,
            platformModule()
        )
    }

fun initKoinDarwin(usercentricsProxy: UsercentricsProxy) =
    startKoin {
        modules(appModule, dispatcherModule, platformModule(), module {
            single<UsercentricsProxy> { usercentricsProxy }
        })
    }

val appModule = module {
    single<Set<CostRule>> {
        setOf(
            WhyDoYouCareRule(),
            GoodCitizenRule(),
            BankingSnoopyRule()
        )
    }
    single<CostCalculatorProcessor> { CostCalculatorProcessor(get()) }
    single<CalculateVirtualCostUsecase> { CalculateVirtualCostUsecase(get(), get()) }
}

val dispatcherModule = module {
    factory { Dispatchers.IO }
}
