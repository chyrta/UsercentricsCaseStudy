package com.usercentrics.test

import com.usercentrics.test.features.costCalculator.domain.model.dataType.DataTypeCost
import com.usercentrics.test.features.costCalculator.domain.model.rule.BankingSnoopyRule
import com.usercentrics.test.features.costCalculator.domain.model.rule.GoodCitizenRule
import com.usercentrics.test.features.costCalculator.domain.model.rule.WhyDoYouCareRule
import com.usercentrics.test.features.costCalculator.domain.usecase.calculateVirtualCost.CostCalculatorProcessor
import com.usercentrics.test.sdk.model.UsercentricsConsentManagementData
import kotlin.test.Test
import kotlin.test.assertEquals


class CostCalculatorProcessorTest {

    @Test
    fun `test calculate with multiple rules`() {
        val rules = setOf(
            BankingSnoopyRule(),
            WhyDoYouCareRule(),
            GoodCitizenRule()
        )

        val calculatorProcessor = CostCalculatorProcessor(rules)

        val dataTypes = listOf(
            DataTypeCost.IpAddress,
            DataTypeCost.Geolocation,
            DataTypeCost.SearchTerms
        )

        val services = listOf(
            UsercentricsConsentManagementData("1", "My company", dataTypes)
        )

        val result = calculatorProcessor.calculateTotalCost(services)
        assertEquals(result, 15.0)
    }



}