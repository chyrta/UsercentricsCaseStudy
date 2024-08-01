package com.usercentrics.test.features.costCalculator.presentation

import com.usercentrics.test.base.UiEffect
import com.usercentrics.test.base.UiEvent
import com.usercentrics.test.base.UiState
import com.usercentrics.test.sdk.model.UsercentricsUserConsent

interface CostCalculatorContract {

    sealed interface Event: UiEvent {
        data object CalculateInitialCost: Event
        data object OnConsentButtonClick: Event
        data class OnConsentProvidedClick(val providedConsents: List<UsercentricsUserConsent>):
            Event
    }

    data class State(
        val totalCost: Int = 0,
        val isReady: Boolean = false,
    ): UiState {
        val totalCostDisplayable = "$totalCost"
    }

    sealed interface Effect: UiEffect {
        data object ShowUsercentricsConsentBanner: Effect
    }

}