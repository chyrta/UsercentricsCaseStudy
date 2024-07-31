package com.usercentrics.test.features.costCalculator.rule

import com.usercentrics.test.features.costCalculator.DataTypeCost
import com.usercentrics.test.features.costCalculator.CostAdjustment
import com.usercentrics.test.features.costCalculator.CostAdjustmentType

class GoodCitizenRule : CostRule {

    override fun costAdjustment(dataTypes: Set<DataTypeCost>): CostAdjustment? {
        return if (dataTypes.size <= 4) CostAdjustment(CostAdjustmentType.Decrease, 0.1) else null
    }

}