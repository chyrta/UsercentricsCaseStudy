package com.usercentrics.test.sdk

import android.content.Context
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.test.UsercentricsConfig
import com.usercentrics.test.sdk.mapper.mapToDataProcessingServices
import com.usercentrics.test.sdk.model.UsercentricsUserConsent
import com.usercentrics.test.sdk.mapper.mapToUserConsentServices
import com.usercentrics.test.sdk.model.UsercentricsDataProcessingService
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class UsercentricsAndroidNative(
    private val context: Context,
    private val usercentrics: Usercentrics
) : UsercentricsProxy {

    override fun initialize() =
        usercentrics.initialize(
            context = context,
            options = UsercentricsOptions(settingsId = UsercentricsConfig.settingsId)
        )

    override suspend fun isReady(): List<UsercentricsUserConsent> {
        return suspendCancellableCoroutine { continuation ->
            usercentrics.isReady({ status ->
                val consents = status.consents.mapToUserConsentServices()
                continuation.resume(consents)
            }, { error -> continuation.resumeWithException(error) })
        }
    }

    override fun getDataProcessingServices(): List<UsercentricsDataProcessingService> {
        return usercentrics.instance
            .getCMPData()
            .services
            .mapToDataProcessingServices()
    }

}