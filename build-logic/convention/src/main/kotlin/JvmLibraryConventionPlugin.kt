import de.grademanager.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Plugin which configures a module as an jvm library
 */
class JvmLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
            }

            configureKotlinJvm()
        }
    }

}