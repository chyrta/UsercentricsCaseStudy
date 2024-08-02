package com.usercentrics.test.features.costCalculator.domain.usecase.reinitializeUsercentrics

import com.usercentrics.test.sdk.UsercentricsProxy

class ReinitializeUsercentricsUsecase(
    private val usercentricsProxy: UsercentricsProxy
) {

    operator fun invoke() = usercentricsProxy.initialize()

}