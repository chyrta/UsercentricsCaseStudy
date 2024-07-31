package com.usercentrics.test.features.costCalculator

data class UsercentricsUserConsent(
    val status: Boolean,
    val templateId: String
)

data class UsercentricsConsentManagementData(
    val templateId: String,
    val processingCompanyName: String,
    val dataTypes: List<DataTypeCost>
)