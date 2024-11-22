plugins {
    alias(libs.plugins.grademanager.android.library)
    alias(libs.plugins.grademanager.koin)

    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "de.grademanager.core.network"
}

dependencies {
    implementation(projects.core.common)

    api(libs.ktor.core)
    implementation(libs.ktor.engine)
    implementation(libs.ktor.contentNegotiation.core)
    implementation(libs.ktor.contentNegotiation.json)
    implementation(libs.ktor.auth)
}