plugins {
    alias(libs.plugins.grademanager.android.library)
    alias(libs.plugins.grademanager.android.datastore)
    alias(libs.plugins.grademanager.koin)
}

android {
    namespace = "de.grademanager.core.data"
}

dependencies {
    api(projects.core.common)
    api(projects.core.model)
    api(projects.core.database)
    api(projects.core.datastore)
}