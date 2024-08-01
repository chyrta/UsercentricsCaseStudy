package com.usercentrics.test.features.costCalculator.presentation

import com.usercentrics.test.base.BaseViewModel
import com.usercentrics.test.sdk.model.UsercentricsUserConsent
import org.koin.core.component.inject
import com.usercentrics.test.features.costCalculator.presentation.CostCalculatorContract.Effect
import com.usercentrics.test.features.costCalculator.presentation.CostCalculatorContract.State
import com.usercentrics.test.features.costCalculator.presentation.CostCalculatorContract.Event
import com.usercentrics.test.features.costCalculator.domain.usecase.calculateVirtualCost.CalculateVirtualCostUsecase
import com.usercentrics.test.features.costCalculator.domain.usecase.prepareUsercentrics.PrepareUsercentricsUsecase
import kotlinx.coroutines.launch

open class CostCalculatorViewModel : BaseViewModel<Event, State, Effect>() {

    private val calculateVirtualCostUseCase: CalculateVirtualCostUsecase by inject()
    private val prepareUsercentricsUsecase: PrepareUsercentricsUsecase by inject()

    init {
        setEvent(Event.CalculateInitialCost)
    }

    override fun createInitialState(): State =
        State(totalCost = 0, isReady = false)

    override fun handleEvent(event: Event) = when (event) {
        is Event.CalculateInitialCost -> calculateInitialVirtualCost()
        is Event.OnConsentButtonClick -> showBannerConsent()
        is Event.OnConsentProvidedClick -> calculateVirtualCost(event.providedConsents)
    }

    private fun calculateInitialVirtualCost() {
        launch {
            val result = prepareUsercentricsUsecase.invoke()
            when {
                result.isSuccess -> {
                    setState { copy(isReady = true) }
                    val result = calculateVirtualCostUseCase(result.getOrNull().orEmpty())
                    setState { copy(totalCost = result) }
                }
            }
        }
    }

    private fun showBannerConsent() = setEffect { Effect.ShowUsercentricsConsentBanner }

    private fun calculateVirtualCost(consents: List<UsercentricsUserConsent>) {
        val result = calculateVirtualCostUseCase(consents)
        setState { copy(totalCost = result) }
    }

}