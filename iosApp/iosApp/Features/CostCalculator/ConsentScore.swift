//
//  ConsentScore.swift
//  iosApp
//
//  Created by Dzmitry Chyrta on 02.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

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

#Preview {
    ConsentScore(score: "10")
}
