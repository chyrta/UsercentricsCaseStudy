package com.usercentrics.test.features.costCalculator.domain.model.calculationResult

data class CalculationResult(
    val results: List<ServiceCalculationResult>,
    val totalCost: Int
)