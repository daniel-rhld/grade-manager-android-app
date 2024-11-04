import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "de.grademanager.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.firebase.performance.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "grademanager.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "grademanager.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidApplication") {
            id = "grademanager.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "grademanager.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("jvmLibrary") {
            id = "grademanager.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        register("androidFeature") {
            id = "grademanager.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("androidRoom") {
            id = "grademanager.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }

        register("androidDataStore") {
            id = "grademanager.android.datastore"
            implementationClass = "AndroidDataStoreConventionPlugin"
        }

        register("koin") {
            id = "grademanager.koin"
            implementationClass = "KoinConventionPlugin"
        }
    }
}