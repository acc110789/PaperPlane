
buildscript {
    repositories {
        mavenLocal()
        maven("http://nexus.corp.kuaishou.com:88/nexus/content/groups/public/")
        maven("http://nexus.corp.kuaishou.com:88/nexus/content/repositories/release/")
        maven("http://maven.aliyun.com/nexus/content/groups/public/")
        google()
        jcenter()
    }

    dependencies {
        classpath(ANDROID_GRADLE_PLUGIN)
        classpath(KOTLIN_GRADLE_PLUGIN)
        classpath(MODULE_PLUGIN)
    }
}


allprojects {
    repositories {
        mavenLocal()
        maven("http://nexus.corp.kuaishou.com:88/nexus/content/groups/public/")
        maven("http://nexus.corp.kuaishou.com:88/nexus/content/repositories/release/")

        maven(url = "http://maven.aliyun.com/nexus/content/groups/public/")
        maven(url = "http://maven.aliyun.com/nexus/content/repositories/jcenter")
        maven(url = "http://maven.aliyun.com/nexus/content/repositories/google")
        maven(url = "http://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
        google()
        jcenter()
    }
}

tasks.create("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}