package com.usercentrics.test.features.costCalculator.domain.model.costAdjusment

sealed class CostAdjustmentType {
    data object Decrease : CostAdjustmentType()
    data object Increase : CostAdjustmentType()
}