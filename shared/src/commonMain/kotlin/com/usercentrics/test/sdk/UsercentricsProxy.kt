package com.usercentrics.test.sdk

import com.usercentrics.test.features.costCalculator.UsercentricsConsentManagementData
import com.usercentrics.test.features.costCalculator.UsercentricsUserConsent

interface UsercentricsProxy {

    fun initialize(settingsId: String)

    fun isReady(onSuccess: (List<UsercentricsUserConsent>) -> Unit, onFailure: (Throwable) -> Unit)

    fun getConsentManagementData(): List<UsercentricsConsentManagementData>

}