package com.usercentrics.test.features.costCalculator.domain.model.costAdjusment

import com.usercentrics.test.util.roundTo

data class CostAdjustment(val type: CostAdjustmentType, val percentage: Double) {

    val formattedPercentage: String
        get() = "${(percentage * 100).roundTo()}%"

}