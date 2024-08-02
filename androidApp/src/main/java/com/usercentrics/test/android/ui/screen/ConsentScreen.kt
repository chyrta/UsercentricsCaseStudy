package com.usercentrics.test.android.ui.screen

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.usercentrics.sdk.UsercentricsBanner
import com.usercentrics.test.android.ui.components.ConsentButton
import com.usercentrics.test.android.ui.components.ConsentErrorDialog
import com.usercentrics.test.android.ui.components.ConsentErrorDialogState
import com.usercentrics.test.android.ui.components.ConsentScore
import com.usercentrics.test.features.costCalculator.presentation.CostCalculatorContract
import com.usercentrics.test.sdk.mapper.mapToUserConsentServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ConsentScreen(
    onUiEvent: (CostCalculatorContract.Event) -> Unit,
    uiState: StateFlow<CostCalculatorContract.State>,
    uiEffect: Flow<CostCalculatorContract.Effect>
) {

    val state by uiState.collectAsStateWithLifecycle()
    val consentErrorDialogState = remember { mutableStateOf<ConsentErrorDialogState>(ConsentErrorDialogState.Hidden) }
    val usercentricsBanner = rememberUsercentricsBanner(activity = LocalContext.current as Activity)

    LaunchedEffect(Unit) {
        uiEffect.collectLatest { effect ->
            when (effect) {
                is CostCalculatorContract.Effect.ShowConsentBanner -> {
                    usercentricsBanner.dismiss()
                    usercentricsBanner.showSecondLayer { response ->
                        val consents = response?.consents.mapToUserConsentServices()
                        onUiEvent(CostCalculatorContract.Event.OnConsentProvidedClick(consents))
                    }
                }
                is CostCalculatorContract.Effect.ShowConsentError -> consentErrorDialogState.value =
                    ConsentErrorDialogState.ShowConsentError(effect.message)

                is CostCalculatorContract.Effect.HideConsentError -> consentErrorDialogState.value =
                    ConsentErrorDialogState.Hidden
            }
        }
    }

    when (val dialogState = consentErrorDialogState.value) {
        is ConsentErrorDialogState.ShowConsentError ->
            ConsentErrorDialog(
                message = dialogState.message,
                onDismiss = { onUiEvent(CostCalculatorContract.Event.OnConsentErrorDismissClick) },
                onRetry = { onUiEvent(CostCalculatorContract.Event.OnConsentErrorRetryClick) }
            )

        is ConsentErrorDialogState.Hidden -> Unit
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            ConsentScore(
                modifier = Modifier.align(Alignment.Center),
                score = state.totalCostDisplayable
            )
            ConsentButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .align(Alignment.BottomCenter),
                enabled = state.isReady,
                onClick = { onUiEvent(CostCalculatorContract.Event.OnShowConsentBannerButtonClick) }
            )
        }
    }

}

@Composable
fun rememberUsercentricsBanner(activity: Activity): UsercentricsBanner {
    return remember { UsercentricsBanner(activity) }
}