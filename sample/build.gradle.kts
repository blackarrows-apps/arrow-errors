plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    kotlin("android")
}

android {
    namespace = "io.blackarrows.errors.sample"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.blackarrows.errors.sample"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Arrow Errors (local project dependencies for development)
    implementation(project(":error-core"))
    implementation(project(":error-catalog"))
    implementation(project(":error-compose"))

    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))

    // Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material.icons.extended)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Activity Compose
    implementation(libs.androidx.activity.compose)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
}
