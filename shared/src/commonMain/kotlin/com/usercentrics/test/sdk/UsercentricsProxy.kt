package com.usercentrics.test.sdk

import com.usercentrics.test.sdk.model.UsercentricsDataProcessingService
import com.usercentrics.test.sdk.model.UsercentricsUserConsent

interface UsercentricsProxy {

    fun initialize()

    suspend fun isReady(): List<UsercentricsUserConsent>

    fun getDataProcessingServices(): List<UsercentricsDataProcessingService>

}