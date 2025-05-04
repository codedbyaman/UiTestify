plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.uitestify"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.uitestify"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material.v160)


    // Activity Compose
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)


    // Material 3
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text)
    implementation(libs.androidx.compose.ui.ui.tooling.preview)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material)
    implementation(platform(libs.androidx.compose.bom.v20240100))
    implementation(libs.material)
    implementation("androidx.appcompat:appcompat:1.6.1")

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Compose BOM (latest stable set)
    //noinspection BomWithoutPlatform
    implementation(libs.androidx.compose.bom.v20240100)

    // Compose UI
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.material.material)
    implementation(libs.androidx.compose.material3.material3)
    implementation(libs.androidx.navigation.compose.v275)

    // Preview, tooling
    implementation(libs.androidx.compose.ui.ui.tooling.preview2)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)

    // ViewModel / lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx.v262)
    implementation(libs.androidx.activity.compose.v182)

    // Test
    androidTestImplementation(libs.androidx.compose.ui.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.ui.test.manifest)

}