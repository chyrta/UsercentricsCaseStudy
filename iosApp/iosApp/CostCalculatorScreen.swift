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
    
    var body: some View {
        VStack {
            Spacer()
            ConsentScore(score: viewModel.state.totalCostDisplayable)
            Spacer()
            ConsentButton {
                viewModel.setEvent(event: CostCalculatorContractEventOnConsentButtonClick.shared)
            }
            .padding(.horizontal, 16)
            .padding(.bottom, 16)
        }.onAppear {
            
            viewModel.collect(flow: viewModel.effect) { uiEffect in
                let effect = (uiEffect as! CostCalculatorContractEffect)

                switch effect {
                case is CostCalculatorContractEffectTriggerConsentDialog:
                    let banner = UsercentricsBanner()
                    banner.showSecondLayer() { userResponse in
                        let consents = userResponse.consents.map { UsercentricsUserConsent(status: $0.status, templateId: $0.templateId) } ?? []
                        viewModel.setEvent(event: CostCalculatorContractEventOnConsentProvidedClick(consents: consents))
                    }
                default:
                    break
                }
            }
        }
    }
}

struct ConsentScore: View {
    let score: String
    
    var body: some View {
        VStack {
            Text(score)
                .font(.system(size: 96, weight: .bold))
            Text("Consent Score")
                .font(.title)
                .padding(.top, 8)
        }
    }
}

struct ConsentButton: View {
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            Text("Show Consent Banner")
                .font(.title3)
                .frame(maxWidth: .infinity)
                .padding()
                .background(Color("PrimaryColor"))
                .foregroundColor(Color.white)
                .cornerRadius(10)
        }
        .frame(height: 50)
    }
}

#Preview {
    CostCalculatorScreen()
}
