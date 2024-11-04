plugins {
    alias(libs.plugins.grademanager.android.feature)
    alias(libs.plugins.grademanager.android.library.compose)
    alias(libs.plugins.grademanager.koin)
}

android {
    namespace = "de.grademanager.feature.subjects.overview"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.feature.subjects.manage)
}