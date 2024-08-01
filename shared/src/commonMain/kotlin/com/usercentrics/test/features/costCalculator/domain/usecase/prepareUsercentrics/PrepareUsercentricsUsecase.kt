package com.usercentrics.test.features.costCalculator.domain.usecase.prepareUsercentrics

import com.usercentrics.test.sdk.UsercentricsProxy
import com.usercentrics.test.sdk.model.UsercentricsUserConsent

class PrepareUsercentricsUsecase(private val usercentrics: UsercentricsProxy) {

    operator suspend fun invoke(): Result<List<UsercentricsUserConsent>> {
        val currentUserConsents = usercentrics.isReady()
        return Result.success(currentUserConsents)
    }

}