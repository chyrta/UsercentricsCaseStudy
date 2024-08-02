package com.usercentrics.test.features.costCalculator.presentation

import com.usercentrics.test.base.BaseViewModel
import com.usercentrics.test.sdk.model.UsercentricsUserConsent
import org.koin.core.component.inject
import com.usercentrics.test.features.costCalculator.presentation.CostCalculatorContract.Effect
import com.usercentrics.test.features.costCalculator.presentation.CostCalculatorContract.State
import com.usercentrics.test.features.costCalculator.presentation.CostCalculatorContract.Event
import com.usercentrics.test.features.costCalculator.domain.usecase.calculateVirtualCost.CalculateVirtualCostUsecase
import com.usercentrics.test.features.costCalculator.domain.usecase.reinitializeUsercentrics.ReinitializeUsercentricsUsecase
import com.usercentrics.test.features.costCalculator.domain.usecase.retrieveCurrentConsentStatus.RetrieveCurrentConsentStatusUsecase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class CostCalculatorViewModel : BaseViewModel<Event, State, Effect>() {

    private val calculateVirtualCostUseCase: CalculateVirtualCostUsecase by inject()
    private val retrieveCurrentConsentStatusUsecase: RetrieveCurrentConsentStatusUsecase by inject()
    private val reinitializeUsercentricsUsecase: ReinitializeUsercentricsUsecase by inject()

    private var calculateVirtualCostJob: Job? = null

    init {
        setEvent(Event.RetrieveCurrentVirtualCost)
    }

    override fun createInitialState(): State =
        State(totalCost = 0, isReady = false)

    override fun handleEvent(event: Event) = when (event) {
        is Event.RetrieveCurrentVirtualCost -> calculateVirtualCost()
        is Event.OnShowConsentBannerButtonClick -> showConsentBanner()
        is Event.OnConsentErrorRetryClick -> retryInitialization()
        is Event.OnConsentProvidedClick -> calculateVirtualCost(event.providedConsents)
        is Event.OnConsentErrorDismissClick -> {
            setEffect { Effect.HideConsentError }
        }
    }

    private fun calculateVirtualCost() {
        calculateVirtualCostJob?.cancel()
        calculateVirtualCostJob = launch {
            retrieveCurrentConsentStatusUsecase().fold(
                onSuccess = {
                    setEffect { Effect.HideConsentError }
                    val initialCost = calculateVirtualCostUseCase(it)
                    setState { copy(totalCost = initialCost, isReady = true) }
                },
                onFailure = {
                    setState { copy(isReady = false) }
                    setEffect { Effect.ShowConsentError(it.message ?: "Unknown error") }
                }
            )
        }
    }

    private fun retryInitialization() {
        reinitializeUsercentricsUsecase()
        calculateVirtualCost()
    }

    private fun showConsentBanner() = setEffect { Effect.ShowConsentBanner }

    private fun calculateVirtualCost(consents: List<UsercentricsUserConsent>) {
        val result = calculateVirtualCostUseCase(consents)
        setState { copy(totalCost = result) }
    }

}