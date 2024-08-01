package com.usercentrics.test.sdk.model

import com.usercentrics.test.features.costCalculator.domain.model.dataType.DataTypeCost

data class UsercentricsConsentManagementData(
    val templateId: String,
    val processingCompanyName: String,
    val dataTypes: List<DataTypeCost>
)