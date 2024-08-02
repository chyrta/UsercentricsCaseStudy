package com.usercentrics.test.features.costCalculator.domain.model.costAdjusment

sealed class CostAdjustmentType(val sign: String) {
    data object Decrease : CostAdjustmentType("-")
    data object Increase : CostAdjustmentType("+")
}