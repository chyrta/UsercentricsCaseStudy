package com.usercentrics.test.sdk

import com.usercentrics.test.sdk.model.UsercentricsConsentManagementData
import com.usercentrics.test.sdk.model.UsercentricsUserConsent

interface UsercentricsProxy {

    fun initialize(settingsId: String)

    suspend fun isReady(): List<UsercentricsUserConsent>

    fun getConsentManagementData(): List<UsercentricsConsentManagementData>

}