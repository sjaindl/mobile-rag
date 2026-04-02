import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildkonfig)
}

val localProperties = Properties()
val localPropertiesFile: File = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

buildConfig {
    packageName = "com.sjaindl.app"
    val flowiseApiKey = localProperties.getProperty("FLOWISE_API_KEY", "")
    buildConfigField("String", "FLOWISE_API_KEY", flowiseApiKey)
}

kotlin {
    jvm()

    android {
        namespace = "com.sjaindl.app.shared"
        compileSdk = 36
        minSdk = 24

        androidResources {
            enable = true
        }

        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.koin.compose)
        }

        jvmMain.dependencies {
            implementation(libs.compose.desktop.currentOs)
        }

        commonMain.dependencies {
            val koinBom = project.dependencies.platform(libs.koin.bom)
            val koinAnnotationsBom =
                project.dependencies.platform(libs.koin.annotations.bom)
            implementation(koinBom)
            implementation(koinAnnotationsBom)
            implementation(libs.koin.core)
            implementation(libs.koin.annotations)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.bundles.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.compose.multiplatform.navigation)

            implementation(project(":assistant"))
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
