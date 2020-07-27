buildscript {
    configBuildScriptRepo()
    dependencies {
        classpath(LibDeps.ANDROID_GRADLE_PLUGIN)
        classpath(LibDeps.KOTLIN_GRADLE_PLUGIN)
    }
}

configAllProjectRepo()

tasks.create("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}