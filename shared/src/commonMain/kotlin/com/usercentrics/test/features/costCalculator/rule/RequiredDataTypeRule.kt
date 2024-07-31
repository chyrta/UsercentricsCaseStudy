package com.usercentrics.test.features.costCalculator.rule

import com.usercentrics.test.features.costCalculator.DataTypeCost
import com.usercentrics.test.features.costCalculator.CostAdjustment

abstract class RequiredDataTypeRule(
    private val requiredDataTypeRule: Set<DataTypeCost>,
    private val costAdjustment: CostAdjustment
) : CostRule {

    override fun costAdjustment(dataTypes: Set<DataTypeCost>): CostAdjustment? {
        return if (dataTypes.containsAll(requiredDataTypeRule)) costAdjustment else null
    }
}