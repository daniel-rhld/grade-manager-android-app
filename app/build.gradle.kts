import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.grademanager.android.application)
    alias(libs.plugins.grademanager.android.application.compose)

    alias(libs.plugins.ksp)
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
    implementation(projects.core.common)
    implementation(projects.core.database)
    implementation(projects.core.datastore)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)

    implementation(projects.feature.auth.register)
    implementation(projects.feature.auth.login)

    implementation(projects.feature.subjects.overview)
    implementation(projects.feature.subjects.detail)
    implementation(projects.feature.subjects.manage)

    implementation(libs.androidx.activity)
    implementation(libs.compose.material3.core)

    implementation(libs.koin.core)

    implementation(libs.navigation)
}