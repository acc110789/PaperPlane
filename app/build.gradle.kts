plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(28)
    buildToolsVersion("28.0.2")

    defaultConfig {
        applicationId = "com.marktony.zhihudaily"
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = 20
        versionName = "V3.3.0"

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

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72")

    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("com.google.android.material:material:1.1.0")
    implementation("androidx.legacy:legacy-preference-v14:1.0.0")
    implementation("androidx.browser:browser:1.2.0")

    implementation("androidx.room:room-runtime:2.2.5")
    kapt("androidx.room:room-compiler:2.2.5")

    implementation("com.squareup.retrofit2:retrofit:2.4.0")
    implementation("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.10.0")

    implementation("com.github.bumptech.glide:glide:4.11.0")
    kapt("com.github.bumptech.glide:compiler:4.11.0")
    implementation("com.github.bumptech.glide:okhttp3-integration:4.11.0")

    implementation("com.wdullaer:materialdatetimepicker:3.6.0") {
        exclude(group = "com.android.support")
    }

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.8")

}
