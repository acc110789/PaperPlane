import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

repositories {
    maven(url = "http://maven.aliyun.com/nexus/content/groups/public/")
    maven(url = "http://maven.aliyun.com/nexus/content/repositories/jcenter")
    maven(url = "http://maven.aliyun.com/nexus/content/repositories/google")
    maven(url = "http://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
    maven(url = "https://repo.gradle.org/gradle/libs-releases-local/")
    jcenter()
    google()
}

dependencies {
//    implementation("com.android.tools.build:gradle:4.0.1")
    implementation("org.gradle:gradle-kotlin-dsl:6.1.1")
}