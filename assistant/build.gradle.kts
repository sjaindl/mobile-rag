import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.vanniktech.maven.publish)
    id("signing")
    alias(libs.plugins.dokka)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

sqldelight {
    databases {
        create("ChatDatabase") {
            packageName.set("com.sjaindl.assistant.database")
        }
    }

    linkSqlite = true
}

group = "io.github.sjaindl"
version = "1.0.0"

kotlin {
    jvm()

    androidLibrary {
        namespace = "com.sjaindl.assistant"
        compileSdk = 36
        minSdk = 24

        withJava()
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

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
            baseName = "assistant"
            isStatic = true
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        commonMain.dependencies {
            val koinBom = project.dependencies.platform(libs.koin.bom)
            val koinAnnotationsBom =
            project.dependencies.platform(libs.koin.annotations.bom)
            implementation(koinBom)
            implementation(koinAnnotationsBom)
            implementation(libs.koin.core)
            implementation(libs.koin.annotations)
            implementation(libs.koin.compose.viewmodel)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.compose.multiplatform.windowSize)
            implementation(libs.compose.multiplatform.adaptive)
            implementation(libs.compose.multiplatform.navigation)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.protobuf)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.kotlinxJson)
            implementation(libs.ktor.contentNegotiation)
            implementation(libs.coil.compose)
            implementation(libs.coil.ktor)
            implementation(libs.logging.napier)
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines.extensions)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.koin.android)
            implementation(libs.koin.compose)
            implementation(libs.sqldelight.android.driver)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
            implementation(libs.sqldelight.native.driver)
        }

        jvmMain.dependencies {
            implementation(libs.sqldelight.sqlite.driver)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.okhttp)
        }
    }
}

signing {
    val signingKeyId = localProperties.getProperty("signing.keyId")
    val signingKeyFile = localProperties.getProperty("signing.secretSigningKeyFile")
    val signingKey = signingKeyFile?.let {
        rootProject.file(it).readText()
    }
    val signingPassword = localProperties.getProperty("signing.password")

    if (signingKeyId != null && signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(
            signingKeyId,
            signingKey,
            signingPassword
        )
    }
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    signAllPublications()

    coordinates(group.toString(), "mobile-rag-assistant", version.toString())

    pom {
        name = "Mobile Rag Assistant"
        description = "Mobile RAG assistant with a ready to use Chatbot UI written in Jetpack Compose. " +
                "Currently Flowise is supported as a platform. " +
                "It has Kotlin Multiplatform support for Android, iOS and Desktop."
        inceptionYear = "2025"
        url = "https://github.com/sjaindl/mobile-rag"

        licenses {
            license {
                name = "MIT Licence"
                url = "https://opensource.org/license/mit"
                distribution = "https://opensource.org/license/mit"
            }
        }

        developers {
            developer {
                id = "sjaindl"
                name = "Stefan Jaindl"
                url = "https://github.com/sjaindl"
            }
        }

        scm {
            url = "https://github.com/sjaindl/mobile-rag"
            connection = "scm:git:git://github.com/sjaindl/mobile-rag.git"
            developerConnection = "scm:git:ssh://git@github.com/sjaindl/mobile-rag.git"
        }
    }
}

tasks.dokkaHtml {
    outputDirectory.set(layout.buildDirectory.dir("documentation/html"))
}

dokka {
    moduleName.set("Mobile RAG Assistant Library")
}
