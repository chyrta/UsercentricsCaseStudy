package com.usercentrics.test.features.costCalculator.domain.usecase.calculateVirtualCost

import com.usercentrics.test.features.costCalculator.domain.model.calculationResult.CalculationResult
import com.usercentrics.test.features.costCalculator.domain.model.calculationResult.ServiceCalculationAppliedRule
import com.usercentrics.test.features.costCalculator.domain.model.calculationResult.ServiceCalculationResult
import com.usercentrics.test.features.costCalculator.domain.model.dataType.DataTypeCost
import com.usercentrics.test.sdk.model.UsercentricsDataProcessingService
import com.usercentrics.test.features.costCalculator.domain.model.costAdjusment.CostAdjustmentType
import com.usercentrics.test.features.costCalculator.domain.model.rule.CostRule
import com.usercentrics.test.util.roundTo
import kotlin.math.roundToInt

class CostCalculatorProcessor(
    private val rules: Set<CostRule>
) {

    fun calculateTotalCost(services: List<UsercentricsDataProcessingService>): CalculationResult {
        val serviceResults = services.map { service ->
            val dataTypes = service.processedDataTypes
            val baseCost = dataTypes.sumOf { it.cost }
            val (finalCost, appliedRules) = applyRules(baseCost, dataTypes.toSet())

            ServiceCalculationResult(
                name = service.processorName,
                baseCost = baseCost,
                dataTypes = dataTypes,
                appliedRules = appliedRules,
                finalCost = finalCost
            )
        }
        val totalCost = serviceResults.sumOf { it.finalCost }
        return CalculationResult(serviceResults, totalCost.roundToInt())
    }

    private fun applyRules(
        baseCost: Int,
        dataTypes: Set<DataTypeCost>
    ): Pair<Double, Set<ServiceCalculationAppliedRule>> {
        var totalCost = baseCost.toDouble()
        val appliedRules = mutableSetOf<ServiceCalculationAppliedRule>()

        rules.forEach { rule ->
            rule.adjustCost(dataTypes)?.let { adjustment ->
                val previousCost = totalCost
                totalCost = when (adjustment.type) {
                    CostAdjustmentType.Increase -> totalCost + (baseCost * adjustment.percentage)
                    CostAdjustmentType.Decrease -> totalCost - (baseCost * adjustment.percentage)
                }
                appliedRules.add(
                    ServiceCalculationAppliedRule(
                        rule = rule,
                        adjustment = adjustment,
                        costBefore = previousCost,
                        costAfter = totalCost,
                    )
                )
            }
        }

        return Pair(totalCost, appliedRules)
    }

}