plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

allprojects {
    group = "io.blackarrows.errors"
    version = "1.0.0-SNAPSHOT"
}
