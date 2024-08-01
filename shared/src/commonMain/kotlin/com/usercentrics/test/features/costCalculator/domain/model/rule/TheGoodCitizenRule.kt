package com.usercentrics.test.features.costCalculator.domain.model.rule

import com.usercentrics.test.features.costCalculator.domain.model.dataType.DataTypeCost
import com.usercentrics.test.features.costCalculator.domain.model.costAdjusment.CostAdjustment
import com.usercentrics.test.features.costCalculator.domain.model.costAdjusment.CostAdjustmentType

class GoodCitizenRule : CostRule {

    override fun costAdjustment(dataTypes: Set<DataTypeCost>): CostAdjustment? {
        return if (dataTypes.size <= 4) CostAdjustment(CostAdjustmentType.Decrease, 0.1) else null
    }

}