plugins {
    alias(libs.plugins.grademanager.android.library)
    alias(libs.plugins.grademanager.koin)
}

android {
    namespace = "de.grademanager.core.domain"
}

dependencies {
    api(projects.core.data)
}