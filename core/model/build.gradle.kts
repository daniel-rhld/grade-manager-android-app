plugins {
    alias(libs.plugins.grademanager.android.library)
}

android {
    namespace = "de.grademanager.core.model"
}

dependencies {
    implementation(projects.core.common)
}