pluginManagement {
    includeBuild("build-logic")

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GradeManager"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:database")
include(":core:data")
include(":core:model")
include(":core:datastore")
include(":core:designsystem")
include(":core:ui")
include(":core:domain")
include(":core:common")

include(":feature:subjects:overview")
include(":feature:subjects:manage")
include(":feature:subjects:detail")

include(":feature:grades")
include(":feature:grades:add")
