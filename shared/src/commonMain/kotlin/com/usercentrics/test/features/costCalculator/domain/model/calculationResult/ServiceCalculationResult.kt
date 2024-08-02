package com.usercentrics.test.features.costCalculator.domain.model.calculationResult

import com.usercentrics.test.features.costCalculator.domain.model.dataType.DataTypeCost

data class ServiceCalculationResult(
    val name: String,
    val baseCost: Int,
    val dataTypes: Set<DataTypeCost>,
    val appliedRules: Set<ServiceCalculationAppliedRule>,
    val finalCost: Double
)