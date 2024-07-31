//
//  UsercentricsDarwinNative.swift
//  iosApp
//
//  Created by Dzmitry Chyrta on 31.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import Usercentrics

class UsercentricsDarwinNative: UsercentricsProxy {

    
    func getConsentManagementData() -> [UsercentricsConsentManagementData] {
        let consentManagementData = UsercentricsCore.shared.getCMPData()
        
        return consentManagementData
            .services
    
//            .filter { $0.templateId?.isEmpty! }
            .map { UsercentricsConsentManagementData(
                templateId: $0.templateId!,
                processingCompanyName: $0.nameOfProcessingCompany,
                dataTypes: $0.dataCollectedList.compactMap { DataTypeCost.companion.fromCode(code: $0) }
            )
            }
        
    }
    
    func isReady(onSuccess: @escaping ([UsercentricsUserConsent]) -> Void, onFailure: @escaping (shared.KotlinThrowable) -> Void) {
        UsercentricsCore.isReady { readyStatus in
            let consents = readyStatus.consents
                .map { UsercentricsUserConsent(status: $0.status, templateId: $0.templateId) }
            onSuccess(consents)
        } onFailure: { error in
            onFailure(KotlinException(message: "Hello world"))
        }
    }
    
    
    func initialize(settingsId: String) {
        let options = UsercentricsOptions(settingsId: settingsId)
        UsercentricsCore.configure(options: options)
    }
    
}
