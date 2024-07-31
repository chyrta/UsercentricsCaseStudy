package com.usercentrics.test.features.costCalculator.rule

import com.usercentrics.test.features.costCalculator.DataTypeCost
import com.usercentrics.test.features.costCalculator.CostAdjustment
import com.usercentrics.test.features.costCalculator.CostAdjustmentType

class WhyDoYouCareRule : RequiredDataTypeRule(
    requiredDataTypeRule = setOf(
        DataTypeCost.IpAddress,
        DataTypeCost.Geolocation,
        DataTypeCost.UserBehaviour
    ),
    costAdjustment = CostAdjustment(CostAdjustmentType.Increase, 0.27)
)