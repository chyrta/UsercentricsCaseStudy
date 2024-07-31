package com.usercentrics.test.features.costCalculator

import com.usercentrics.test.sdk.UsercentricsProxy
import kotlin.math.roundToInt

class CalculateVirtualCostUsecase(
    val usercentricsProxy: UsercentricsProxy,
    private val costCalculatorProcessor: CostCalculatorProcessor
) {

    fun invoke(consentedService: List<UsercentricsUserConsent>): Int {
        val computedDataServices = usercentricsProxy.getConsentManagementData()
        val filteredServices = computedDataServices.filter { service ->
            consentedService.any { it.templateId == service.templateId && it.status == true }
        }

        val totalCost = costCalculatorProcessor.calculate(filteredServices)
        return totalCost.roundToInt()
    }

}