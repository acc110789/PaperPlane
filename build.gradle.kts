buildscript {
    configBuildScriptRepo()
    configBuildScriptClasspath(LibDeps.ANDROID_GRADLE_PLUGIN, LibDeps.KOTLIN_GRADLE_PLUGIN)
}

rootProject.apply {
    configAllProjectRepo()
}

tasks.create("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}