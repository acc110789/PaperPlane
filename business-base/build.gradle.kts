plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion(AndroidConfig.BUILD_TOOL_VERSION)

    defaultConfig {
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)

        javaCompileOptions {
            annotationProcessorOptions {
                argument("MODULE_PACKAGE_NAME", "com.famous.paperplane." + project.name)
            }
        }
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    api(KOTLIN_STDLIB)
    api(APPCOMPAT)
    api(MATERIAL)
    api(LEGACY)
    api(BROWSER)
    api(ROOM)

    api(RETROFIT)
    api(RETROFIT_CONVERTER_GSON)

    api(OK_LOGGING)

    api(GLIDE)
    api(OK_GLIDE)

    api(DATE_TIME_PICKER) {
        exclude(group = "com.android.support")
    }

    api(COROUTINES)
    api(COROUTINES_ANDROID)


    kapt(ROOM_COMPILER)
    kapt(GLIDE_COMPILER)

    api(MODULE_ANDROID)
    api(MODULE_BASE)
}