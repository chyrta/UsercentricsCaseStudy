//
//  CostCalculatorVM.swift
//  iosApp
//
//  Created by Dzmitry Chyrta on 31.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import UsercentricsUI

class CostCalculatorVM: CostCalculatorViewModel, ObservableObject {
    @Published var state: CostCalculatorContractState = CostCalculatorContractState(totalCost: 0, isReady: true)
    @Published var showBanner: Bool = false
    
    override init() {
        super.init()
        
        collect(flow: uiState, collect: { state in
            self.state = state as! CostCalculatorContractState
        })
    }
    
}
