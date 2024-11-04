import de.grademanager.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Plugin which configures koin in a module
 */
class KoinConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", libs.findLibrary("koin.core").get())
            }
        }
    }

}