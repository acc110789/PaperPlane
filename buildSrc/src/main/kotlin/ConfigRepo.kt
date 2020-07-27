import org.gradle.api.Project
import org.gradle.kotlin.dsl.ScriptHandlerScope
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories

fun ScriptHandlerScope.configBuildScriptRepo() = repositories {
    maven(url = "http://maven.aliyun.com/nexus/content/groups/public/")
    google()
    jcenter()
}

fun ScriptHandlerScope.configBuildScriptClasspath(vararg paths: String) = dependencies {
    paths.forEach { classpath(it) }
}

fun Project.configAllProjectRepo() = allprojects {
    repositories {
        maven(url = "http://maven.aliyun.com/nexus/content/groups/public/")
        maven(url = "http://maven.aliyun.com/nexus/content/repositories/jcenter")
        maven(url = "http://maven.aliyun.com/nexus/content/repositories/google")
        maven(url = "http://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
        google()
        jcenter()
    }
}
