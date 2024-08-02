package com.usercentrics.test

import com.usercentrics.test.features.costCalculator.domain.model.costAdjusment.CostAdjustment
import com.usercentrics.test.features.costCalculator.domain.model.costAdjusment.CostAdjustmentType
import com.usercentrics.test.features.costCalculator.domain.model.dataType.DataTypeCost
import com.usercentrics.test.sdk.model.UsercentricsDataProcessingService
import kotlin.random.Random

fun Set<DataTypeCost>.applyExpectedAdjustments(
    baseCost: Int,
    expectedAdjustments: List<CostAdjustment>
): Double {
    var totalCost = baseCost.toDouble()
    expectedAdjustments.forEach { adjustment ->
        totalCost = when (adjustment.type) {
            CostAdjustmentType.Increase -> totalCost + (baseCost * adjustment.percentage)
            CostAdjustmentType.Decrease -> totalCost - (baseCost * adjustment.percentage)
        }
    }
    return totalCost
}

fun mockService(dataTypes: Set<DataTypeCost>) =
    UsercentricsDataProcessingService(Random.toString(), Random.toString(), dataTypes)