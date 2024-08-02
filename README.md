## Overview
An application for both Android & iOS, written with Kotlin Multiplatform, utilizing the Usercentrics SDK to calculate the virtual cost of given consent to the app.

According to the case study description, three rules can be used for calculating virtual cost. However, this solution is designed to provide flexible addition of new rules with different criteria such as data types, percentage change, and increase/decrease. Additionally, this solution includes detailed logging of calculation results to understand how the rules affect the final cost. Here is an example:
```
3. Service Name: Google Analytics
	Base Cost: 8
	Data Types: Date and time of visit (1 points), IP address (2 points), Browser information (3 points), User behaviour (2 points)
	Applied Rules:
		The good citizen (-10.0% (Decrease 0.8 points))
	Total Cost After: 7.2
```

<image src="assets/screenshot_1.png" width="250px">|<image src="assets/screenshot_2.png" width="250px">

## Setup
1. Place Usercentrics API Key into `local.properties` in the root folder of the project. 
```
usercentrics.settingsId=abcdefg
``` 
2. Make sure you have [Cocoapods](https://cocoapods.org/) installed for iOS dependencies.
3. Navigate to `iosApp` folder and run `pod install` to install dependencies for iOS part such as Usercentrics SDK.
3. Run `./gradlew build` from the root folder to sync and build the whole project.

## Components
- MVI architecture (inspired by [Daniel Avila Domingo project](https://github.com/daniaviladomingo/kmm)
- Jetpack Compose
- SwiftUI
- Kotlin Coroutines/Flow
- Usercentrics SDK
- BuildKonfig

## Project Structure
The project is utilizing feature-based approach and contains several modules:
- **androidApp:** Android version of the case study app 
- **iosApp** iOS version of the case study app that contains Swift code related to UI and working with view models
- **shared** Core components for business logic related to calculation and sharing Usercentrics SDK via proxy between platforms

## What things can improved?
- Probably, getting rid of `UsercentricsProxy` component by introducing native iOS Usercentrics SDK
  in `shared` module. I noticed, that Usercentrics SDK for iOS is also written in Kotlin Multiplatform :)
- Better project structure, better namings, better placing of extension functions and mappers
- Unit tests not only for cost calculation processor, but also for the rest of business logic