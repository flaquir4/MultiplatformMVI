
pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlin-multiplatform") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
            }
        }
    }
}
rootProject.name = "JSAndroid"
enableFeaturePreview("GRADLE_METADATA")
include(":common")
include(":web")
include(":androidapp")
