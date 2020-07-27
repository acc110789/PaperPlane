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

    implementation(LibDependency.KOTLIN_STDLIB)

    implementation(LibDependency.APPCOMPAT)
    implementation(LibDependency.MATERIAL)
    implementation(LibDependency.LEGACY)
    implementation(LibDependency.BROWSER)

    implementation(LibDependency.ROOM)
    kapt(LibDependency.ROOM_COMPILER)

    implementation(LibDependency.RETROFIT)
    implementation(LibDependency.RETROFIT_CONVERTER_GSON)
    implementation(LibDependency.OK_LOGGING)

    implementation(LibDependency.GLIDE)
    implementation(LibDependency.OK_GLIDE)
    kapt(LibDependency.GLIDE_COMPILER)

    implementation(LibDependency.DATE_TIME_PICKER) {
        exclude(group = "com.android.support")
    }

    implementation(LibDependency.COROUTINES)
    implementation(LibDependency.COROUTINES_ANDROID)

}
