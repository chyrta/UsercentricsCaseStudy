package com.usercentrics.test.features.costCalculator

sealed class DataTypeCost(val code: String, val cost: Int) {
    data object ConfigurationAppSettings : DataTypeCost("Configuration of app settings", 1)
    data object IpAddress : DataTypeCost("IP address", 2)
    data object UserBehaviour : DataTypeCost("User behaviour", 2)
    data object UserAgent : DataTypeCost("User agent", 3)
    data object AppCrashes : DataTypeCost("App crashes", -2)
    data object BrowserInformation : DataTypeCost("Browser information", 3)
    data object CardNumber : DataTypeCost("Credit and debit card number", 4)
    data object FirstName : DataTypeCost("First Name", 6)
    data object Geolocation : DataTypeCost("Geographic location", 7)
    data object DateTimeVisit : DataTypeCost("Date and time of visit", 1)
    data object AdvertisingIdentifier : DataTypeCost("Advertising identifier", 2)
    data object BankDetails : DataTypeCost("Bank details", 5)
    data object PurchaseActivity : DataTypeCost("Purchase activity", 6)
    data object InternetServiceProvider : DataTypeCost("Internet service provider", 4)
    data object JavaScriptSupport : DataTypeCost("JavaScript support", -1)

    companion object {
        fun fromCode(code: String): DataTypeCost? {
            return when (code) {
                "Configuration of app settings" -> ConfigurationAppSettings
                "IP address" -> IpAddress
                "User behaviour" -> UserBehaviour
                "User agent" -> UserAgent
                "App crashes" -> AppCrashes
                "Browser information" -> BrowserInformation
                "Credit and debit card number" -> CardNumber
                "First Name" -> FirstName
                "Geographic location" -> Geolocation
                "Date and time of visit" -> DateTimeVisit
                "Advertising identifier" -> AdvertisingIdentifier
                "Bank details" -> BankDetails
                "Purchase activity" -> PurchaseActivity
                "Internet service provider" -> InternetServiceProvider
                "JavaScript support" -> JavaScriptSupport
                else -> null
            }
        }
    }
}