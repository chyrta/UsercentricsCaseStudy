package com.usercentrics.test.android.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ConsentButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .height(50.dp),
        onClick = { onClick.invoke() },
        enabled = enabled,
        shape = RoundedCornerShape(20)
    ) {
        Text("Show Consent Banner")
    }
}