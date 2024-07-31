## Overview

An application for both Android & iOS which is written with Kotlin Multiplatform and which utilizes
Usercentrics SDK in order to calculate the virtual cost of given consent to the app.

|<image src="assets/screenshot_1.png" width="250px">|<image src="assets/screenshot_2.png" width="250px">|

## Components
- MVI architecture (inspired by [Daniel Avila Domingo project](https://github.com/daniaviladomingo/kmm)
- Jetpack Compose
- SwiftUI
- Kotlin Coroutines/Flow
- Usercentrics SDK

## Project Structure
The project is utilizing feature-based approach and contains several modules:
- **androidApp:** Android version of the case study app 
- **iosApp** iOS version of the case study app that contains Swift code related to UI and working with view models
- **shared** Core components for business logic related to calculation and sharing Usercentrics SDK via proxy between platforms