import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    id("kotlin-parcelize")
}

android {
    defaultConfig {
        namespace = "de.grademanager"
        applicationId = "de.grademanager"

        minSdk = 26
        targetSdk = 34
        compileSdk = 34

        versionCode = 1
        versionName = "1.0"

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity)
    implementation(libs.multidex)

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
}