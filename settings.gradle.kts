import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = File(rootDir, "local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "mobile-rag"
include(":assistant")
include(":composeApp")
include(":androidApp")
