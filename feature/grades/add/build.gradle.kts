plugins {
    alias(libs.plugins.grademanager.android.feature)
    alias(libs.plugins.grademanager.android.library.compose)
}

android {
    namespace = "de.grademanager.feature.grades.add"
}

dependencies {
    implementation(projects.core.common)
}