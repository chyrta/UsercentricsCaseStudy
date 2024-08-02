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
    
    func getDataProcessingServices() -> [UsercentricsDataProcessingService] {
        return UsercentricsCore.shared.getCMPData()
            .services
            .filter { $0.templateId != nil && $0.dataProcessor != nil }
            .map {
                UsercentricsDataProcessingService(
                    templateId: $0.templateId!,
                    processorName: $0.dataProcessor!,
                    processedDataTypes: Set($0.dataCollectedList.compactMap { DataTypeCost.companion.fromCode(code: $0) })
                )
            }
    }
    
    func isReadyAsync() async throws -> [UsercentricsUserConsent] {
        return await withCheckedContinuation { continuation in
            UsercentricsCore.isReady { readyStatus in
                let consents = readyStatus
                    .consents
                    .map { UsercentricsUserConsent(status: $0.status, templateId: $0.templateId) }
                continuation.resume(returning: consents)
            } onFailure: { error in
                continuation.resume(throwing: error as! Never)
            }
        }
    }
    
    func isReady(completionHandler: @escaping ([UsercentricsUserConsent]?, Error?) -> Void) {
        UsercentricsCore.isReady { readyStatus in
            let consents = readyStatus
                .consents
                .map { UsercentricsUserConsent(status: $0.status, templateId: $0.templateId) }
            completionHandler(consents, nil)
        } onFailure: { error in
            completionHandler(nil, error)
        }
    }

    func initialize() {
        let options = UsercentricsOptions(settingsId: UsercentricsConfig().settingsId)
        UsercentricsCore.configure(options: options)
    }
    
}
