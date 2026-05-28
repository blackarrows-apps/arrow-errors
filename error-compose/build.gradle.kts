import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "io.blackarrows.errors.compose"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

kotlin {
    androidTarget {
        publishLibraryVariants("release", "debug")
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    jvm()

    js(IR) {
        browser()
        nodejs()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    // Note: iOS, macOS, and other native targets can be added when needed

    sourceSets {
        commonMain {
            dependencies {
                api(project(":error-core"))
                api(project(":error-catalog"))

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.materialIconsExtended)
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

group = "io.github.blackarrows-apps"
version = "1.1.2"

mavenPublishing {
    coordinates("io.github.blackarrows-apps", "arrow-errors-compose", "1.1.2")

    pom {
        name.set("Arrow Errors Compose")
        description.set("Compose Multiplatform UI components for displaying actionable errors")
        url.set("https://github.com/blackarrows-apps/arrow-errors")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("E5c11")
                name.set("Emmanuel Conradie")
                url.set("https://github.com/E5c11")
            }
        }

        scm {
            connection.set("scm:git:git://github.com/blackarrows-apps/arrow-errors.git")
            developerConnection.set("scm:git:ssh://github.com/blackarrows-apps/arrow-errors.git")
            url.set("https://github.com/blackarrows-apps/arrow-errors")
        }
    }

    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}
