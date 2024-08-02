package com.usercentrics.test.features.costCalculator.domain.model.calculationResult

import com.usercentrics.test.features.costCalculator.domain.model.costAdjusment.CostAdjustment
import com.usercentrics.test.features.costCalculator.domain.model.rule.CostRule
import com.usercentrics.test.util.roundTo
import kotlin.math.abs

data class ServiceCalculationAppliedRule(
    val rule: CostRule,
    val adjustment: CostAdjustment,
    val costBefore: Double,
    val costAfter: Double
) {

    val name: String = rule.name

    private val costDifference = abs(costBefore - costAfter).roundTo()
    val description: String
        get() = buildString {
            append(adjustment.type.sign)
            append(adjustment.formattedPercentage)
            append(" (${adjustment.type} $costDifference points)")
        }

}