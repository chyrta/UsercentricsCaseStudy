package com.usercentrics.test.android

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsBanner
import com.usercentrics.test.features.costCalculator.CostCalculatorContract
import com.usercentrics.test.features.costCalculator.UsercentricsUserConsent
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
    val activity = LocalContext.current as Activity
    val usercentricsBanner by lazy { UsercentricsBanner(activity) }

    LaunchedEffect(Unit) {
        uiEffect.collectLatest { effect ->
            when (effect) {
                is CostCalculatorContract.Effect.TriggerConsentDialog -> {
                    usercentricsBanner.dismiss()
                    usercentricsBanner.showSecondLayer { response ->
                        val consents = response?.consents?.map { UsercentricsUserConsent(it.status, it.templateId) }.orEmpty()
                        onUiEvent.invoke(CostCalculatorContract.Event.OnConsentProvidedClick(consents))
                    }

                }
            }
        }
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
                    .align(Alignment.BottomCenter)
            ) {
                onUiEvent(CostCalculatorContract.Event.OnConsentButtonClick)
            }
        }
    }

}