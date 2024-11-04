package de.grademanager

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            val bom = libs.findLibrary("compose.bom").get()

            add(configurationName = "implementation", dependencyNotation = platform(bom))
            add(configurationName = "androidTestImplementation", dependencyNotation = platform(bom))
            add(
                configurationName = "implementation",
                dependencyNotation = libs.findLibrary("debugging.preview").get()
            )
            add(
                configurationName = "debugImplementation",
                dependencyNotation = libs.findLibrary("debugging.tooling").get()
            )
        }

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }

        extensions.configure<ComposeCompilerGradlePluginExtension> {
            fun Provider<String>.onlyIfTrue() = flatMap { provider { it.takeIf(String::toBoolean) } }
            fun Provider<*>.relativeToRootProject(dir: String) = flatMap {
                rootProject.layout.buildDirectory.dir(projectDir.toRelativeString(rootDir))
            }.map { it.dir(dir) }

            project.providers.gradleProperty("enableComposeCompilerMetrics").onlyIfTrue()
                .relativeToRootProject("compose-metrics")
                .let(metricsDestination::set)

            project.providers.gradleProperty("enableComposeCompilerReports").onlyIfTrue()
                .relativeToRootProject("compose-reports")
                .let(reportsDestination::set)

            stabilityConfigurationFile =
                rootProject.layout.projectDirectory.file("compose_compiler_config.conf")
        }
    }
}