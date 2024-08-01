package com.usercentrics.test.di

import com.usercentrics.test.features.costCalculator.domain.usecase.calculateVirtualCost.CalculateVirtualCostUsecase
import com.usercentrics.test.features.costCalculator.domain.usecase.calculateVirtualCost.CostCalculatorProcessor
import com.usercentrics.test.features.costCalculator.domain.model.rule.BankingSnoopyRule
import com.usercentrics.test.features.costCalculator.domain.model.rule.CostRule
import com.usercentrics.test.features.costCalculator.domain.model.rule.GoodCitizenRule
import com.usercentrics.test.features.costCalculator.domain.model.rule.WhyDoYouCareRule
import com.usercentrics.test.features.costCalculator.domain.usecase.prepareUsercentrics.PrepareUsercentricsUsecase
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
    single<PrepareUsercentricsUsecase> { PrepareUsercentricsUsecase(get()) }
}

val dispatcherModule = module {
    factory { Dispatchers.IO }
}
