buildscript {
    repositories {
        maven(url = "http://maven.aliyun.com/nexus/content/groups/public/")
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
    }
}

allprojects {
    repositories {
        maven(url = "http://maven.aliyun.com/nexus/content/groups/public/")
        maven(url = "http://maven.aliyun.com/nexus/content/repositories/jcenter")
        maven(url = "http://maven.aliyun.com/nexus/content/repositories/google")
        maven(url = "http://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
        google()
        jcenter()
    }
}
//
//task clean(type: Delete) {
//    delete(rootProject.buildDir)
//}
