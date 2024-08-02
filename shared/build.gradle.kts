import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.util.*

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.buildKonfig)
}

val localPropertiesFile = project.rootProject.file("local.properties")
val localProperties = Properties()
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

val usercentricsSettingsId = localProperties.getProperty("usercentrics.settingsId") ?: ""

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
        androidMain.dependencies {
            api(libs.usercentrics.sdk.android)
            api(libs.koin.android)
            implementation(libs.kotlinx.coroutines.android)
        }
        commonMain.dependencies {
            api(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

buildkonfig {
    packageName = "com.usercentrics.test"
    exposeObjectWithName = "UsercentricsConfig"

    defaultConfigs {
        buildConfigField(STRING, "settingsId", usercentricsSettingsId)
    }
}

android {
    namespace = "com.usercentrics.test"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
