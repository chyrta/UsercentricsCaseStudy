package com.usercentrics.test.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.usercentrics.test.android.ui.screen.ConsentScreen
import com.usercentrics.test.android.ui.theme.UsercentricsTheme
import com.usercentrics.test.features.costCalculator.presentation.CostCalculatorViewModel
import org.koin.android.ext.android.inject

class UsercentricsMainActivity : ComponentActivity() {

    private val costCalculatorViewModel: CostCalculatorViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UsercentricsTheme {
                ConsentScreen(
                    onUiEvent = { event -> costCalculatorViewModel.setEvent(event) },
                    uiState = costCalculatorViewModel.uiState,
                    uiEffect = costCalculatorViewModel.effect
                )
            }
        }
    }
}
