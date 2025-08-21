plugins {
    val kotlin = "2.2.10"
    kotlin("multiplatform") version kotlin
    kotlin("plugin.serialization") version kotlin
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js().nodejs()
    if (org.gradle.internal.os.OperatingSystem.current().isMacOsX) {
        iosX64()
        iosArm64()
        iosSimulatorArm64()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                val kotlinx = "org.jetbrains.kotlinx:kotlinx"
                implementation("$kotlinx-coroutines-core:1.10.2")
                implementation("$kotlinx-datetime:0.7.1")
                implementation("$kotlinx-serialization-json:1.9.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}