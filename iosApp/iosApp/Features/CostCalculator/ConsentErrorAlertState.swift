//
//  ConsentErrorAlertState.swift
//  iosApp
//
//  Created by Dzmitry Chyrta on 02.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

enum ConsentErrorAlertState: Equatable {
    case hidden
    case showConsentError(message: String)
}
