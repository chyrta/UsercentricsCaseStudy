package com.usercentrics.test.features.costCalculator.domain.usecase.calculateVirtualCost

import com.usercentrics.test.features.costCalculator.domain.model.calculationResult.CalculationResult

object CostCalculatorLogger {

    fun logCalculationDetails(calculationResult: CalculationResult) {
        println("Total Cost: ${calculationResult.totalCost}")
        calculationResult.results.forEachIndexed { index, service ->
            println("${index + 1}. Service Name: ${service.name}")
            println("\tBase Cost: ${service.baseCost}")
            println("\tData Types: ${service.dataTypes.joinToString(", ") { "${it.code} (${it.cost} points)" }}")
            println("\tApplied Rules:")
            service.appliedRules.forEach { rule ->
                println("\t\t${rule.name} (${rule.description})")
            }
            println("\tTotal Cost After: ${service.finalCost}")
        }
    }

}