package com.usercentrics.test

import com.usercentrics.test.features.costCalculator.domain.model.costAdjusment.CostAdjustment
import com.usercentrics.test.features.costCalculator.domain.model.costAdjusment.CostAdjustmentType
import com.usercentrics.test.features.costCalculator.domain.model.dataType.DataTypeCost
import com.usercentrics.test.sdk.model.UsercentricsDataProcessingService
import kotlin.math.roundToInt
import kotlin.random.Random

fun applyExpectedAdjustments(
    baseCost: Int,
    expectedAdjustments: List<CostAdjustment>
): Int {
    var totalCost = baseCost.toDouble()
    expectedAdjustments.forEach { adjustment ->
        totalCost = when (adjustment.type) {
            CostAdjustmentType.Increase -> totalCost + (baseCost * adjustment.percentage)
            CostAdjustmentType.Decrease -> totalCost - (baseCost * adjustment.percentage)
        }
    }
    return totalCost.roundToInt()
}

fun mockService(dataTypes: Set<DataTypeCost>) =
    UsercentricsDataProcessingService(Random.toString(), Random.toString(), dataTypes)