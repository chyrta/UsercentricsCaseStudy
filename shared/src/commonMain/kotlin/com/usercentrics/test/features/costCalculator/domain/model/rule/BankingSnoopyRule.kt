package com.usercentrics.test.features.costCalculator.domain.model.rule

import com.usercentrics.test.features.costCalculator.domain.model.dataType.DataTypeCost
import com.usercentrics.test.features.costCalculator.domain.model.costAdjusment.CostAdjustment
import com.usercentrics.test.features.costCalculator.domain.model.costAdjusment.CostAdjustmentType

class BankingSnoopyRule(override val name: String = "Banking Snoopy") : RequiredDataTypeRule(
    requiredDataTypeRule = setOf(
        DataTypeCost.PurchaseActivity,
        DataTypeCost.BankDetails,
        DataTypeCost.CardNumber
    ),
    costAdjustment = CostAdjustment(CostAdjustmentType.Increase, 0.1)
)