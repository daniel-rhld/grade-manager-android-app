plugins {
    alias(libs.plugins.grademanager.android.library)
    alias(libs.plugins.grademanager.android.datastore)
    alias(libs.plugins.grademanager.koin)
}

android {
    namespace = "de.grademanger.core.datastore"
}

dependencies {
    api(projects.core.model)
}