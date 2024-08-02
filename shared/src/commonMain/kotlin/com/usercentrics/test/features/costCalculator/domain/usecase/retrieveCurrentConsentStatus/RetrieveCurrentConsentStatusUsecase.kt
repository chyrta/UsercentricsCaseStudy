package com.usercentrics.test.features.costCalculator.domain.usecase.retrieveCurrentConsentStatus

import com.usercentrics.test.sdk.UsercentricsProxy
import com.usercentrics.test.sdk.model.UsercentricsUserConsent

class RetrieveCurrentConsentStatusUsecase(private val usercentricsSdk: UsercentricsProxy) {

    suspend operator fun invoke(): Result<List<UsercentricsUserConsent>> {
        try {
            val currentUserConsents = usercentricsSdk.isReady()
            return Result.success(currentUserConsents)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

}