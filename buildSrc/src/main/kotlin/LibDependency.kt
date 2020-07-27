

object LibDependency {
    private const val KOTLIN_VERSION = "1.3.72"

    const val ANDROID_GRADLE_PLUGIN = "com.android.tools.build:gradle:4.0.1"
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"

    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$KOTLIN_VERSION"

    const val APPCOMPAT = "androidx.appcompat:appcompat:1.1.0"
    const val MATERIAL = "com.google.android.material:material:1.1.0"
    const val LEGACY = "androidx.legacy:legacy-preference-v14:1.0.0"
    const val BROWSER = "androidx.browser:browser:1.2.0"

    private const val ROOM_VERSION = "2.2.5"
    const val ROOM = "androidx.room:room-runtime:$ROOM_VERSION"
    const val ROOM_COMPILER = "androidx.room:room-compiler:$ROOM_VERSION"

    private const val RETROFIT_VERSION = "2.4.0"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
    const val RETROFIT_CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION"

    const val OK_LOGGING = "com.squareup.okhttp3:logging-interceptor:3.10.0"

    private const val GLIDE_VERSION = "4.11.0"
    const val GLIDE = "com.github.bumptech.glide:glide:$GLIDE_VERSION"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:$GLIDE_VERSION"
    const val OK_GLIDE = "com.github.bumptech.glide:okhttp3-integration:$GLIDE_VERSION"

    const val DATE_TIME_PICKER = "com.wdullaer:materialdatetimepicker:3.6.0"

    private const val COROUTINES_VERSION = "1.3.8"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES_VERSION"
    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VERSION"
}