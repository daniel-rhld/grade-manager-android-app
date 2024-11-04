import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    defaultConfig {
        namespace = "de.grademanager"
        applicationId = "de.grademanager"

        minSdk = 26
        targetSdk = 35
        compileSdk = 35

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "org.junit.runners.JUnit4"
        testInstrumentationRunnerArguments["runnerBuilder"] = "de.mannodermaus.junit5.AndroidJUnit5Builder"
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.core.data)
    implementation(projects.core.domain)

    implementation(projects.feature.subjects.overview)

    implementation(libs.androidx.core)
    implementation(libs.androidx.activity)
    //implementation(libs.multidex)

    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3.core)
    implementation(libs.compose.material3.windowSize)
    implementation(libs.compose.icons.core)
    implementation(libs.compose.icons.extended)
    implementation(libs.compose.constraintLayout)

    implementation(libs.navigation.core)
    ksp(libs.navigation.compiler)

    implementation(libs.room.core)
    implementation(libs.room.extensions)
    ksp(libs.room.compiler)

    implementation(libs.datastore.preferences)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.collections)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.koin.core)

    implementation(libs.debugging.tooling)
    debugImplementation(libs.debugging.manifest)
    debugImplementation(libs.debugging.preview)

    testImplementation(libs.test.junit5.core)
    testRuntimeOnly(libs.test.junit5.engine)
}