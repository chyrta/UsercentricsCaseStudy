package com.usercentrics.test.features.costCalculator

sealed class CostAdjustmentType {
    data object Decrease : CostAdjustmentType()
    data object Increase : CostAdjustmentType()
}