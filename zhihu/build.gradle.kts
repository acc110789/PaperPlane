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
    api(project(":business-base"))

    kapt(ROOM_COMPILER)
    kapt(GLIDE_COMPILER)
    kapt(LIFECYCLE_COMPILER)
}