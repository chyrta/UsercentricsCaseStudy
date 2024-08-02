//
//  CostCalculatorScreen.swift
//  iosApp
//
//  Created by Dzmitry Chyrta on 31.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import UsercentricsUI
import shared


struct CostCalculatorScreen: View {
    
    @ObservedObject var viewModel: CostCalculatorVM = CostCalculatorVM()
    
    @State private var consentErrorAlertState: ConsentErrorAlertState = .hidden
    
    private let usercentricsBanner: UsercentricsBanner = {
        let banner = UsercentricsBanner()
        return banner
    }()
    
    var body: some View {
        VStack {
            Spacer()
            ConsentScore(score: viewModel.state.totalCostDisplayable)
            Spacer()
            ConsentButton(
                onClick: {
                    viewModel.setEvent(event: CostCalculatorContractEventOnShowConsentBannerButtonClick.shared)
                },
                disabled: !viewModel.state.isReady
            )
            .padding(.horizontal, 16)
            .padding(.bottom, 16)
            .alert(isPresented: Binding<Bool>(
                get: { consentErrorAlertState != .hidden },
                set: { if !$0 { consentErrorAlertState = .hidden } }
            )) {
                switch (consentErrorAlertState) {
                case .showConsentError(let message):
                    return Alert(
                        title: Text("Initialization Error"),
                        message: Text(message),
                        primaryButton: .default(Text("Retry")) {
                            viewModel.setEvent(
                                event: CostCalculatorContractEventOnConsentErrorRetryClick.shared
                            )
                        },
                        secondaryButton: .cancel {
                            viewModel.setEvent(
                                event: CostCalculatorContractEventOnConsentErrorDismissClick.shared
                            )
                        }
                    )
                case .hidden:
                    return Alert(title: Text(""))
                }
            }
        }.onAppear {
            viewModel.collect(flow: viewModel.effect) { uiEffect in
                let effect = (uiEffect as! CostCalculatorContractEffect)
                switch effect {
                case is CostCalculatorContractEffectShowConsentBanner:
                    usercentricsBanner.showSecondLayer() { userResponse in
                        let consents = userResponse
                            .consents
                            .map { UsercentricsUserConsent(status: $0.status, templateId: $0.templateId) }
                        viewModel.setEvent(
                            event: CostCalculatorContractEventOnConsentProvidedClick(providedConsents: consents)
                        )
                    }
                case let effect as CostCalculatorContractEffectShowConsentError:
                    consentErrorAlertState = .showConsentError(message: effect.message)
                case is CostCalculatorContractEffectHideConsentError:
                    consentErrorAlertState = .hidden
                default:
                    break
                }
            }
        }
    }
}

#Preview {
    CostCalculatorScreen()
}
