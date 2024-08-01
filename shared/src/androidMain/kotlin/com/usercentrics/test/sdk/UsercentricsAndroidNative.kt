package com.usercentrics.test.sdk

import android.content.Context
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.test.features.costCalculator.domain.model.dataType.DataTypeCost
import com.usercentrics.test.sdk.model.UsercentricsConsentManagementData
import com.usercentrics.test.sdk.model.UsercentricsUserConsent
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class UsercentricsAndroidNative(
    private val context: Context,
    private val usercentrics: Usercentrics
) : UsercentricsProxy {

    override fun initialize(settingsId: String) =
        usercentrics.initialize(context, UsercentricsOptions(settingsId = settingsId))

    override suspend fun isReady(): List<UsercentricsUserConsent> {
        return suspendCancellableCoroutine { continuation ->
            usercentrics.isReady({ status ->
                val consents = status.consents.map {
                    UsercentricsUserConsent(status = it.status, templateId = it.templateId)
                }
                continuation.resume(consents)
            }, { error -> continuation.resumeWithException(error) })
        }
    }

    override fun getConsentManagementData(): List<UsercentricsConsentManagementData> {
        return usercentrics.instance
            .getCMPData()
            .services
            .filterNot { it.templateId.isNullOrEmpty() }
            .map {
                UsercentricsConsentManagementData(
                    templateId = it.templateId!!,
                    processingCompanyName = it.nameOfProcessingCompany,
                    dataTypes = it.dataCollectedList.mapNotNull { DataTypeCost.fromCode(it) }
                )
            }
    }

}