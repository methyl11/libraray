// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.firebase.perf) apply false
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.24" apply false
}

//dependencies {
//    classpath("com.google.firebase:perf-plugin:1.1.0") {
//        exclude(group = "com.google.guava", module = "guava-jdk5")
//    }
//}