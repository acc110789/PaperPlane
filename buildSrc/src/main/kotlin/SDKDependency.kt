private const val KOTLIN_VERSION = "1.4.10"

const val ANDROID_GRADLE_PLUGIN = "com.android.tools.build:gradle:4.0.1"
const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
const val ANDROID_KOTLIN_EXTENSION = "org.jetbrains.kotlin:kotlin-android-extensions:$KOTLIN_VERSION"

const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION"

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

private const val COROUTINES_VERSION = "1.3.9"
const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES_VERSION"
const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VERSION"

const val MODULE_ANDROID = "com.kwai.kim.module_suite:module-android:0.0.3"
const val MODULE_BASE = "com.kwai.kim.module_suite:module-base:0.0.1"
const val MODULE_COMPILER = "com.kwai.kim.module_suite:module-compiler:0.0.2"
const val MODULE_PLUGIN = "com.kwai.kim.module_suite:module-plugin:0.0.8"
const val MODULE_PLUGIN_NAME = "com.kwai.kim.module"

const val ROUTER_ANNOTATION = "com.kwai.kim.router_suite:router-annotation:0.0.1"
const val ROUTER_ANDROID = "com.kwai.kim.router_suite:router-android:0.0.3"
const val ROUTER_COMPILER = "com.kwai.kim.router_suite:router-compiler:0.0.1"
const val ROUTER_PLUGIN = "com.kwai.kim.router_suite:router-plugin:0.0.2"


private const val lifecycle_version = "2.2.0"
// ViewModel
const val VIEW_MODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
// LiveData
const val LIVE_DATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
// Saved state module for ViewModel
const val VIEW_MODEL_SAVE_STATE = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"
// Annotation processor
const val LIFECYCLE_COMPILER ="androidx.lifecycle:lifecycle-compiler:$lifecycle_version"
// optional - helpers for implementing LifecycleOwner in a Service
const val LIFECYCLE_SERVICE = "androidx.lifecycle:lifecycle-service:$lifecycle_version"
// optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
const val LIFECYCLE_PROCESS = "androidx.lifecycle:lifecycle-process:$lifecycle_version"


// Stable Koin Version
private const val koin_version = "2.2.0-rc-2"
// Koin for Android
const val KOIN_ANDROID = "org.koin:koin-android:$koin_version"
// or Koin for Lifecycle scoping
const val KOIN_ANDROIDX_SCOPE = "org.koin:koin-androidx-scope:$koin_version"
// or Koin for Android Architecture ViewModel
const val KOIN_VIEWMODEL = "org.koin:koin-androidx-viewmodel:$koin_version"
// or Koin for Android Fragment Factory (unstable version)
const val KOIN_ANDROIDX_FRAGMENT = "org.koin:koin-androidx-fragment:$koin_version"