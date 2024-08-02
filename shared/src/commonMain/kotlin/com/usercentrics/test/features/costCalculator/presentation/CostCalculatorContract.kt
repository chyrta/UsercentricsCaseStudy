package com.usercentrics.test.features.costCalculator.presentation

import com.usercentrics.test.base.UiEffect
import com.usercentrics.test.base.UiEvent
import com.usercentrics.test.base.UiState
import com.usercentrics.test.sdk.model.UsercentricsUserConsent

interface CostCalculatorContract {

    sealed interface Event: UiEvent {
        data object RetrieveCurrentVirtualCost: Event
        data object OnConsentErrorDismissClick: Event
        data object OnConsentErrorRetryClick: Event
        data object OnShowConsentBannerButtonClick: Event
        data class OnConsentProvidedClick(
            val providedConsents: List<UsercentricsUserConsent>
        ): Event
    }

    data class State(
        val totalCost: Int = 0,
        val isReady: Boolean = false,
    ): UiState {
        val totalCostDisplayable = "$totalCost"
    }

    sealed interface Effect: UiEffect {
        data object ShowConsentBanner: Effect
        data class ShowConsentError(val message: String): Effect
        data object HideConsentError: Effect
    }

}