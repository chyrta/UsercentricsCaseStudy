package com.usercentrics.test.sdk.model

import com.usercentrics.test.features.costCalculator.domain.model.dataType.DataTypeCost

data class UsercentricsDataProcessingService(
    val templateId: String,
    val processorName: String,
    val processedDataTypes: Set<DataTypeCost>
)