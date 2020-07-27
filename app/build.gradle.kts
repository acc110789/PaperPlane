plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion(AndroidConfig.BUILD_TOOL_VERSION)

    defaultConfig {
        applicationId = "com.marktony.zhihudaily"
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
        versionCode = AndroidConfig.APP_VERSION_CODE
        versionName = AndroidConfig.APP_VERSION_NAME

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")

        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {
    implementation(project(":arch"))

    implementation(fileTree("include" to listOf("*.jar"), "dir" to "libs"))

    implementation(LibDeps.KOTLIN_STDLIB)

    implementation(LibDeps.APPCOMPAT)
    implementation(LibDeps.MATERIAL)
    implementation(LibDeps.LEGACY)
    implementation(LibDeps.BROWSER)

    implementation(LibDeps.ROOM)
    kapt(LibDeps.ROOM_COMPILER)

    implementation(LibDeps.RETROFIT)
    implementation(LibDeps.RETROFIT_CONVERTER_GSON)
    implementation(LibDeps.OK_LOGGING)

    implementation(LibDeps.GLIDE)
    implementation(LibDeps.OK_GLIDE)
    kapt(LibDeps.GLIDE_COMPILER)

    implementation(LibDeps.DATE_TIME_PICKER) {
        exclude(group = "com.android.support")
    }

    implementation(LibDeps.COROUTINES)
    implementation(LibDeps.COROUTINES_ANDROID)

}
