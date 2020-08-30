buildscript {
    addBuildScriptRepos()
    
    addBuildScriptClassPath(
            LibDependency.ANDROID_GRADLE_PLUGIN,
            LibDependency.KOTLIN_GRADLE_PLUGIN
    )
}

rootProject.apply {
    addAllProjectRepos()
}

tasks.create("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}