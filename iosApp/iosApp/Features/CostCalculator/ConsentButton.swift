//
//  ConsentButton.swift
//  iosApp
//
//  Created by Dzmitry Chyrta on 02.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ConsentButton: View {
    let onClick: () -> Void
    let disabled: Bool
    
    var body: some View {
        Button(action: onClick) {
            Text("Show Consent Banner")
                .font(.title3)
                .frame(maxWidth: .infinity)
                .padding()
                .background(disabled ? Color.gray : Color("PrimaryColor"))
                .foregroundColor(Color.white)
                .cornerRadius(10)
        }
        .disabled(disabled)
        .frame(height: 50)
    }
}

#Preview {
    ConsentButton(onClick: {}, disabled: false)
}
