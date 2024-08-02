package com.usercentrics.test

import com.usercentrics.test.features.costCalculator.domain.model.dataType.DataTypeCost
import com.usercentrics.test.features.costCalculator.domain.model.rule.BankingSnoopyRule
import com.usercentrics.test.features.costCalculator.domain.model.rule.TheGoodCitizenRule
import com.usercentrics.test.features.costCalculator.domain.model.rule.WhyDoYouCareRule
import com.usercentrics.test.features.costCalculator.domain.usecase.calculateVirtualCost.CostCalculatorProcessor
import kotlin.math.roundToInt
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CostCalculatorProcessorTest {

    private lateinit var costCalculatorProcessor: CostCalculatorProcessor

    private lateinit var bankingSnoopyRule: BankingSnoopyRule
    private lateinit var whyDoYouCareRule: WhyDoYouCareRule
    private lateinit var theGoodCitizenRule: TheGoodCitizenRule

    @BeforeTest
    fun setUp() {
        bankingSnoopyRule = BankingSnoopyRule()
        whyDoYouCareRule = WhyDoYouCareRule()
        theGoodCitizenRule = TheGoodCitizenRule()

        val rules = setOf(
            bankingSnoopyRule,
            whyDoYouCareRule,
            theGoodCitizenRule
        )

        costCalculatorProcessor = CostCalculatorProcessor(rules)
    }

    @Test
    fun `when no rules are applied the base cost is returned`() {
        val dataTypes = setOf(
            DataTypeCost.AdvertisingIdentifier,
            DataTypeCost.UserAgent,
            DataTypeCost.BrowserInformation,
            DataTypeCost.AppCrashes,
            DataTypeCost.DateTimeVisit
        )

        val services = listOf(mockService(dataTypes))

        val expectedCost = dataTypes.sumOf { it.cost }
        val result = costCalculatorProcessor.calculateTotalCost(services)
        assertEquals(expectedCost, result.totalCost)
    }

    @Test
    fun `when there are purchase activity bank details number and card number details both good citizen and banking snoopy are applied`() {
        val dataTypes = setOf(
            DataTypeCost.PurchaseActivity,
            DataTypeCost.BankDetails,
            DataTypeCost.CardNumber
        )

        val services = listOf(mockService(dataTypes))

        val expectedCost = applyExpectedAdjustments(
            baseCost = dataTypes.sumOf { it.cost },
            expectedAdjustments = listOf(
                bankingSnoopyRule.costAdjustment,
                theGoodCitizenRule.costAdjustment
            )
        )

        val result = costCalculatorProcessor.calculateTotalCost(services)
        assertEquals(expectedCost, result.totalCost)
    }

    @Test
    fun `when two rules are applied all cost adjustments are applied correctly`() {
        val dataTypes = setOf(
            DataTypeCost.PurchaseActivity,
            DataTypeCost.BankDetails,
            DataTypeCost.CardNumber,
            DataTypeCost.IpAddress,
            DataTypeCost.Geolocation,
            DataTypeCost.SearchTerms
        )

        val services = listOf(mockService(dataTypes))

        val expectedCost = applyExpectedAdjustments(
            baseCost = dataTypes.sumOf { it.cost },
            expectedAdjustments = listOf(
                bankingSnoopyRule.costAdjustment,
                whyDoYouCareRule.costAdjustment
            )
        )

        val result = costCalculatorProcessor.calculateTotalCost(services)
        assertEquals(expectedCost, result.totalCost)
    }


}