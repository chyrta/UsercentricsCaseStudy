package com.usercentrics.test.features.costCalculator.rule

import com.usercentrics.test.features.costCalculator.DataTypeCost
import com.usercentrics.test.features.costCalculator.CostAdjustment

interface CostRule {
    fun costAdjustment(dataTypes: Set<DataTypeCost>): CostAdjustment?
}