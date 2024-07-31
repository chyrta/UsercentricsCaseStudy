package com.usercentrics.test.features.costCalculator

import com.usercentrics.test.base.UiEffect
import com.usercentrics.test.base.UiEvent
import com.usercentrics.test.base.UiState

interface CostCalculatorContract {

    sealed interface Event: UiEvent {
        data object OnConsentButtonClick: Event
        data class OnConsentProvidedClick(val consents: List<UsercentricsUserConsent>): Event
    }

    data class State(
        val totalCost: Int,
        val isReady: Boolean
    ): UiState {

        val totalCostDisplayable = "$totalCost"
    }

    sealed interface Effect: UiEffect {
        data object TriggerConsentDialog: Effect
    }

}