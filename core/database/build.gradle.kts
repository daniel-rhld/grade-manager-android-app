plugins {
    alias(libs.plugins.grademanager.android.library)
    alias(libs.plugins.grademanager.android.room)
    alias(libs.plugins.grademanager.koin)
}

android {
    namespace = "de.grademanager.core.database"
}

dependencies {
    api(projects.core.model)
}