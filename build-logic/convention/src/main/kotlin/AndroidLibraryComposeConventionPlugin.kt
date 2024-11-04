import com.android.build.gradle.LibraryExtension
import de.grademanager.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType

/**
 * Plugin which adds Jetpack Compose to a library module
 */
class AndroidLibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")

            configureAndroidCompose(
                commonExtension = extensions.getByType<LibraryExtension>()
            )
        }
    }

}