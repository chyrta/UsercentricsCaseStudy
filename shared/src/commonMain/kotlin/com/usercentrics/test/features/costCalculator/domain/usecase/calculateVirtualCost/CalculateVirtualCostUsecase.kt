package com.usercentrics.test.features.costCalculator.domain.usecase.calculateVirtualCost

import com.usercentrics.test.sdk.model.UsercentricsUserConsent
import com.usercentrics.test.sdk.UsercentricsProxy
import kotlin.math.roundToInt

class CalculateVirtualCostUsecase(
    private val usercentricsSdk: UsercentricsProxy,
    private val costCalculatorProcessor: CostCalculatorProcessor
) {

    operator fun invoke(consentedService: List<UsercentricsUserConsent>): Int {
        // Getting list of available data processing services with companies and their data types
        val dataProcessingServices = usercentricsSdk.getDataProcessingServices()

        // Filtering the result by leaving only those
        // where consent was given (status = true) and its template id matches
        val filteredServices = dataProcessingServices.filter { service ->
            consentedService.any { it.templateId == service.templateId && it.status }
        }

        // Calculating and logging total cost of the services
        val costCalculationResult = costCalculatorProcessor.calculateTotalCost(filteredServices)
        CostCalculatorLogger.logCalculationDetails(costCalculationResult)
        return costCalculationResult.totalCost
    }

}