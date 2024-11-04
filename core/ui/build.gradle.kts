plugins {
    alias(libs.plugins.grademanager.android.library)
    alias(libs.plugins.grademanager.android.library.compose)
}

android {
    namespace = "de.grademanager.core.ui"
}

dependencies {
    api(projects.core.model)
    api(projects.core.designsystem)
    api(projects.core.common)
}