plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    kotlin("android") version "2.1.0" apply false
}

allprojects {
    group = "io.blackarrows.errors"
    version = "1.0.0-SNAPSHOT"
}
