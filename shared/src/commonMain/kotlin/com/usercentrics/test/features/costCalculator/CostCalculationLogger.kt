package com.usercentrics.test.features.costCalculator

object CostCalculationLogger {
    fun logCalculationDetails(services: List<ServiceCalculationDetail>, totalCost: Int) {
        println("Total Cost: $totalCost")
        println()
        services.forEachIndexed { index, service ->
            println("${index + 1}. Service Name: ${service.name}")
            println("\tBase Cost: ${service.baseCost}")
            println("\tData Types: ${service.dataTypes.joinToString(", ") { "${it.code} (${it.cost} points)" }}")
            println("\tApplied Rules:")
            service.appliedRules.forEach { rule ->
                println("\t\t${rule.name} (${rule.description})")
            }
            println("\tTotal Cost After: ${service.totalCost}")
            println()
        }
    }

    data class ServiceCalculationDetail(
        val name: String,
        val baseCost: Int,
        val dataTypes: List<DataTypeCost>,
        val appliedRules: List<AppliedRule>,
        val totalCost: Int
    )

    data class AppliedRule(val name: String, val description: String)
}