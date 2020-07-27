buildscript {
    configBuildScriptRepo()
    
    configBuildScriptClasspath(
            LibDependency.ANDROID_GRADLE_PLUGIN,
            LibDependency.KOTLIN_GRADLE_PLUGIN
    )
}

rootProject.apply {
    configAllProjectRepo()
}

tasks.create("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}