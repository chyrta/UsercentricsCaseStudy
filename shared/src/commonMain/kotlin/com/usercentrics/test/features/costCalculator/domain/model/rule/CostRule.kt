package com.usercentrics.test.features.costCalculator.domain.model.rule

import com.usercentrics.test.features.costCalculator.domain.model.dataType.DataTypeCost
import com.usercentrics.test.features.costCalculator.domain.model.costAdjusment.CostAdjustment

interface CostRule {
    fun costAdjustment(dataTypes: Set<DataTypeCost>): CostAdjustment?
}