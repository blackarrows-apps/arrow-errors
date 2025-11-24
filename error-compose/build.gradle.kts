plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    `maven-publish`
}

group = "io.blackarrows.errors"
version = "1.0.0"

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

    // Note: iOS, macOS, and other native targets can be added when needed
    // Uncomment below when you need native support:
    // iosX64()
    // iosArm64()
    // iosSimulatorArm64()
    // macosX64()
    // macosArm64()
    // wasmJs {
    //     browser()
    // }

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

publishing {
    publications {
        withType<MavenPublication> {
            groupId = "io.blackarrows.errors"
            artifactId = "error-compose-$name"
            version = project.version.toString()

            pom {
                name.set("Arrow Errors Compose")
                description.set("Compose Multiplatform UI components for displaying actionable errors")
                url.set("https://github.com/blackarrows/arrow-errors")

                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                }

                developers {
                    developer {
                        id.set("blackarrows")
                        name.set("Black Arrows")
                    }
                }
            }
        }
    }

    repositories {
        mavenLocal()
    }
}
