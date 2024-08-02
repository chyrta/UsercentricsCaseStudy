package com.usercentrics.test.features.costCalculator.domain.model.rule

import com.usercentrics.test.features.costCalculator.domain.model.dataType.DataTypeCost
import com.usercentrics.test.features.costCalculator.domain.model.costAdjusment.CostAdjustment
import com.usercentrics.test.features.costCalculator.domain.model.costAdjusment.CostAdjustmentType

class TheGoodCitizenRule(
    override val name: String = "The good citizen",
    override val costAdjustment: CostAdjustment = CostAdjustment(CostAdjustmentType.Decrease, 0.1)
) : CostRule {

    override fun adjustCost(dataTypes: Set<DataTypeCost>): CostAdjustment? {
        return if (dataTypes.size <= 4) costAdjustment else null
    }

}