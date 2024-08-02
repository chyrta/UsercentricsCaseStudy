package com.usercentrics.test.di

import com.usercentrics.test.features.costCalculator.domain.usecase.calculateVirtualCost.CalculateVirtualCostUsecase
import com.usercentrics.test.features.costCalculator.domain.usecase.calculateVirtualCost.CostCalculatorProcessor
import com.usercentrics.test.features.costCalculator.domain.model.rule.BankingSnoopyRule
import com.usercentrics.test.features.costCalculator.domain.model.rule.CostRule
import com.usercentrics.test.features.costCalculator.domain.model.rule.TheGoodCitizenRule
import com.usercentrics.test.features.costCalculator.domain.model.rule.WhyDoYouCareRule
import com.usercentrics.test.features.costCalculator.domain.usecase.reinitializeUsercentrics.ReinitializeUsercentricsUsecase
import com.usercentrics.test.features.costCalculator.domain.usecase.retrieveCurrentConsentStatus.RetrieveCurrentConsentStatusUsecase
import com.usercentrics.test.sdk.UsercentricsProxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}, extraModules: List<Module> = listOf()) =
    startKoin {
        appDeclaration()
        modules(
            listOf(
                costCalculationFeatureModule,
                dispatcherModule,
                platformModule()
            ) + extraModules
        )
    }

fun initKoinDarwin(usercentricsProxy: UsercentricsProxy) = initKoin(
    extraModules = listOf(module {
        single<UsercentricsProxy> { usercentricsProxy }
    })
)

val dispatcherModule = module {
    factory { Dispatchers.IO }
}

val costCalculationFeatureModule = module {
    single<Set<CostRule>> {
        setOf(
            WhyDoYouCareRule(),
            TheGoodCitizenRule(),
            BankingSnoopyRule()
        )
    }
    single<CostCalculatorProcessor> { CostCalculatorProcessor(get()) }
    single<CalculateVirtualCostUsecase> { CalculateVirtualCostUsecase(get(), get()) }
    single<RetrieveCurrentConsentStatusUsecase> { RetrieveCurrentConsentStatusUsecase(get()) }
    single<ReinitializeUsercentricsUsecase> { ReinitializeUsercentricsUsecase(get()) }
}
