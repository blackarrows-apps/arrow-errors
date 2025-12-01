plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.maven.publish)
}

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
                implementation(libs.kotlinx.io)
                api(libs.kotlinx.serialization)
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

mavenPublishing {
    coordinates("io.github.blackarrows-apps", "arrow-errors-core", "1.0.1")

    pom {
        name.set("Arrow Errors Core")
        description.set("Core error handling types and interfaces for Kotlin Multiplatform")
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
