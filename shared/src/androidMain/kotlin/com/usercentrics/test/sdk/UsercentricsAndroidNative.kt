package com.usercentrics.test.sdk

import android.content.Context
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsBanner
import com.usercentrics.sdk.UsercentricsConsentUserResponse
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.sdk.UsercentricsReadyStatus
import com.usercentrics.sdk.errors.UsercentricsError
import com.usercentrics.test.features.costCalculator.DataTypeCost
import com.usercentrics.test.features.costCalculator.UsercentricsConsentManagementData
import com.usercentrics.test.features.costCalculator.UsercentricsUserConsent
import kotlinx.coroutines.flow.Flow

class UsercentricsAndroidNative(
    private val context: Context,
    private val usercentrics: Usercentrics
) : UsercentricsProxy {

    override fun initialize(settingsId: String) {
        usercentrics.initialize(context, UsercentricsOptions(settingsId = settingsId))
    }

    override fun isReady(
        onSuccess: (List<UsercentricsUserConsent>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        usercentrics.isReady({ status ->
            val consents = status.consents.map {
                UsercentricsUserConsent(status = it.status, templateId = it.templateId)
            }
            onSuccess(consents)
        }, { error ->
            onFailure(error)
        })
    }

//    override fun requestUserConsentBanner(onConsent: (List<UsercentricsUserConsent>) -> Unit) {
//        val userCentricsBanner = UsercentricsBanner(context)
//        userCentricsBanner.showSecondLayer { response ->
//            val consents = response?.consents?.map {
//                UsercentricsUserConsent(status = it.status, templateId = it.templateId)
//            } ?: emptyList()
//            onConsent(consents)
//        }
//    }

    override fun getConsentManagementData(): List<UsercentricsConsentManagementData> {
        val computedData = usercentrics.instance.getCMPData()
        return computedData
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