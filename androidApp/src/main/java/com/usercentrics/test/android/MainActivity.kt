package com.usercentrics.test.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.usercentrics.test.features.costCalculator.CostCalculatorViewModel
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

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
