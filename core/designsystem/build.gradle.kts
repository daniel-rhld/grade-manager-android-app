plugins {
    alias(libs.plugins.grademanager.android.library)
    alias(libs.plugins.grademanager.android.library.compose)
}

android {
    namespace = "de.grademanager.core.designsystem"
}

dependencies {
    api(libs.compose.material3.core)
    api(libs.compose.icons.core)
    api(libs.compose.icons.extended)
}