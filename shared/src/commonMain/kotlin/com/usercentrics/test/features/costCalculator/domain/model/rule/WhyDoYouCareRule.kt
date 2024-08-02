package com.usercentrics.test.features.costCalculator.domain.model.rule

import com.usercentrics.test.features.costCalculator.domain.model.dataType.DataTypeCost
import com.usercentrics.test.features.costCalculator.domain.model.costAdjusment.CostAdjustment
import com.usercentrics.test.features.costCalculator.domain.model.costAdjusment.CostAdjustmentType

class WhyDoYouCareRule(override val name: String = "Why do you care?") : RequiredDataTypeRule(
    requiredDataTypeRule = setOf(
        DataTypeCost.SearchTerms,
        DataTypeCost.Geolocation,
        DataTypeCost.IpAddress
    ),
    costAdjustment = CostAdjustment(CostAdjustmentType.Increase, 0.27)
)