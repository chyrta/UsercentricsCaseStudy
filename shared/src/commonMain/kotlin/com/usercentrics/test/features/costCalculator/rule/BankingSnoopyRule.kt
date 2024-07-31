package com.usercentrics.test.features.costCalculator.rule

import com.usercentrics.test.features.costCalculator.DataTypeCost
import com.usercentrics.test.features.costCalculator.CostAdjustment
import com.usercentrics.test.features.costCalculator.CostAdjustmentType

class BankingSnoopyRule : RequiredDataTypeRule(
    requiredDataTypeRule = setOf(
        DataTypeCost.PurchaseActivity,
        DataTypeCost.BankDetails,
        DataTypeCost.CardNumber
    ),
    costAdjustment = CostAdjustment(CostAdjustmentType.Increase, 0.1)
)