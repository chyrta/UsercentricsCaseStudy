package com.usercentrics.test.android.ui.components

sealed class ConsentErrorDialogState {
    data object Hidden: ConsentErrorDialogState()
    data class ShowConsentError(val message: String): ConsentErrorDialogState()
}