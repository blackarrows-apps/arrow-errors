plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    `maven-publish`
}

group = "io.blackarrows.errors"
version = "1.0.0"

kotlin {
    jvm()

    js(IR) {
        browser()
        nodejs()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    macosX64()
    macosArm64()

    linuxX64()
    linuxArm64()

    mingwX64()

    sourceSets {
        commonMain {
            dependencies {
                api(project(":error-core"))
                api(libs.kotlinx.serialization)
                api(libs.koin.core)
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
            artifactId = "error-catalog-$name"
            version = project.version.toString()

            pom {
                name.set("Arrow Errors Catalog")
                description.set("Shared error catalog for all platforms with consistent error codes and messages")
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
        // Add your remote Maven repository here if needed
        // maven {
        //     url = uri("https://your-maven-repo.com/releases")
        //     credentials {
        //         username = findProperty("maven.username") as String?
        //         password = findProperty("maven.password") as String?
        //     }
        // }
    }
}
