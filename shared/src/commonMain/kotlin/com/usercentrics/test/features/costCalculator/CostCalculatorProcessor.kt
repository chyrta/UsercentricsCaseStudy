package com.usercentrics.test.features.costCalculator

import com.usercentrics.test.features.costCalculator.rule.CostRule
import kotlin.math.roundToInt

class CostCalculatorProcessor(
    private val rules: Set<CostRule>
) {

    fun calculate(services: List<UsercentricsConsentManagementData>): Double {
        val serviceDetails = services.map { service ->
            val dataTypes = service.dataTypes
            val baseCost = dataTypes.sumOf { it.cost }.toDouble()
            val (finalCost, appliedRules) = applyRulesIndependently(baseCost, dataTypes.toSet())

            CostCalculationLogger.ServiceCalculationDetail(
                name = service.processingCompanyName,
                baseCost = baseCost.roundToInt(),
                dataTypes = dataTypes,
                appliedRules = appliedRules,
                totalCost = finalCost.roundToInt()
            )
        }

        val totalCost = serviceDetails.sumOf { it.totalCost }
        CostCalculationLogger.logCalculationDetails(serviceDetails, totalCost)
        return totalCost.toDouble()
    }

    private fun applyRulesIndependently(baseCost: Double, dataTypes: Set<DataTypeCost>): Pair<Double, List<CostCalculationLogger.AppliedRule>> {
        var totalCost = baseCost
        val appliedRules = mutableListOf<CostCalculationLogger.AppliedRule>()

        rules.forEach { rule ->
            rule.costAdjustment(dataTypes)?.let { adjustment ->
                val previousCost = totalCost
                totalCost = when (adjustment.type) {
                    CostAdjustmentType.Increase -> totalCost + (baseCost * adjustment.percentage)
                    CostAdjustmentType.Decrease -> totalCost - (baseCost * adjustment.percentage)
                }
                appliedRules.add(
                    CostCalculationLogger.AppliedRule(
                        name = rule::class.simpleName ?: "Unknown Rule",
                        description = "${if (adjustment.type == CostAdjustmentType.Increase) "+" else "-"}${adjustment.percentage * 100}% (${adjustment.type} ${previousCost - totalCost} points)"
                    )
                )
            }
        }

        return Pair(totalCost, appliedRules)
    }

}