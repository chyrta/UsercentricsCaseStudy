import SwiftUI
import shared

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
	var body: some Scene {
		WindowGroup {
			CostCalculatorScreen()
		}
	}
}

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        let userCentricsDarwinNative = UsercentricsDarwinNative()
        userCentricsDarwinNative.initialize(settingsId: "gChmbFIdL")
        
        KoinKt.doInitKoinDarwin(usercentricsProxy: userCentricsDarwinNative)
        return true
    }
}
