plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
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

    implementation(KOTLIN_STDLIB)

    implementation(APPCOMPAT)
    implementation(MATERIAL)
    implementation(LEGACY)
    implementation(BROWSER)

    implementation(ROOM)
    kapt(ROOM_COMPILER)

    implementation(RETROFIT)
    implementation(RETROFIT_CONVERTER_GSON)
    implementation(OK_LOGGING)

    implementation(GLIDE)
    implementation(OK_GLIDE)
    kapt(GLIDE_COMPILER)

    implementation(DATE_TIME_PICKER) {
        exclude(group = "com.android.support")
    }

    implementation(COROUTINES)
    implementation(COROUTINES_ANDROID)

}
