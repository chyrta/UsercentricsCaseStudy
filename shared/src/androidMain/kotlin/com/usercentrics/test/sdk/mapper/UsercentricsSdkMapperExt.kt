package com.usercentrics.test.sdk.mapper

import com.usercentrics.sdk.UsercentricsServiceConsent
import com.usercentrics.sdk.v2.settings.data.UsercentricsService
import com.usercentrics.test.features.costCalculator.domain.model.dataType.DataTypeCost
import com.usercentrics.test.sdk.model.UsercentricsDataProcessingService
import com.usercentrics.test.sdk.model.UsercentricsUserConsent

fun List<UsercentricsServiceConsent>?.mapToUserConsentServices(): List<UsercentricsUserConsent> =
    this?.map { UsercentricsUserConsent(it.status, it.templateId) }.orEmpty()

fun List<UsercentricsService>.mapToDataProcessingServices(): List<UsercentricsDataProcessingService> =
    this
        .filterNot { it.templateId == null && it.dataProcessor == null }
        .map {
            UsercentricsDataProcessingService(
                templateId = it.templateId!!,
                processorName = it.dataProcessor!!,
                processedDataTypes = it.dataCollectedList.mapToDataTypes()
            )
        }

fun List<String>.mapToDataTypes(): Set<DataTypeCost> =
    this.mapNotNull { DataTypeCost.fromCode(it) }.toSet()