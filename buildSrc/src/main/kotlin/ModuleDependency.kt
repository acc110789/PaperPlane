import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

val DependencyHandler.arch: ProjectDependency get() = project(":arch")

val DependencyHandler.app: ProjectDependency get() = project(":app")
