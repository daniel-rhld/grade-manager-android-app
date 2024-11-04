plugins {
    alias(libs.plugins.grademanager.android.library)
}

android {
    namespace = "de.grademanager.core.common"
}

dependencies {
    implementation(libs.androidx.core)
}