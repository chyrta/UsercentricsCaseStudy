package com.usercentrics.test.features.costCalculator

import com.usercentrics.test.base.BaseViewModel
import org.koin.core.component.inject

open class CostCalculatorViewModel :
    BaseViewModel<CostCalculatorContract.Event, CostCalculatorContract.State, CostCalculatorContract.Effect>() {

    private val calculateVirtualCostUseCase: CalculateVirtualCostUsecase by inject()

    override fun createInitialState(): CostCalculatorContract.State =
        CostCalculatorContract.State(totalCost = 0, isReady = false)

    override fun handleEvent(event: CostCalculatorContract.Event) {
        when (event) {
            is CostCalculatorContract.Event.OnConsentButtonClick -> showBannerConsent()
            is CostCalculatorContract.Event.OnConsentProvidedClick -> calculateVirtualCost(event.consents)
        }
    }

    private fun showBannerConsent() {
        if (currentState.isReady) {
            setEffect { CostCalculatorContract.Effect.TriggerConsentDialog }
        } else {
            calculateVirtualCostUseCase.usercentricsProxy.isReady({
                setState { copy(isReady = true) }
                setEffect { CostCalculatorContract.Effect.TriggerConsentDialog }
            }, {

            })
        }
    }

    private fun calculateVirtualCost(consents: List<UsercentricsUserConsent>) {
        val result = calculateVirtualCostUseCase.invoke(consents)
        setState { copy(totalCost = result) }
    }

}