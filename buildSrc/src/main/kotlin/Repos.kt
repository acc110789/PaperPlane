import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.ScriptHandlerScope
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories

fun ScriptHandlerScope.addBuildScriptRepos() = repositories { Repos.buildScriptRepo(this) }

fun ScriptHandlerScope.addBuildScriptClassPath(vararg paths: String) = dependencies {
    paths.forEach { classpath(it) }
}

fun Project.addAllProjectRepos() = allprojects { repositories { Repos.projectRepo(this) } }

private object Repos {

    fun buildScriptRepo(handler: RepositoryHandler) = handler.apply {
        maven(url = "http://maven.aliyun.com/nexus/content/groups/public/")
        google()
        jcenter()
    }

    fun projectRepo(handler: RepositoryHandler) = handler.apply {
        maven(url = "http://maven.aliyun.com/nexus/content/groups/public/")
        maven(url = "http://maven.aliyun.com/nexus/content/repositories/jcenter")
        maven(url = "http://maven.aliyun.com/nexus/content/repositories/google")
        maven(url = "http://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
        google()
        jcenter()
    }
}
