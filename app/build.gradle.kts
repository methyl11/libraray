import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.include

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.firebase.perf)
   // id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
    kotlin("kapt")

}

android {
    namespace = "com.x3lnthpi.library"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.x3lnthpi.library"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "META-INF/*.kotlin_module",
                "META-INF/INDEX.LIST",
                "META-INF/google/protobuf/**",
                "META-INF/services/**"
            )
        )
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.espresso.core)
    implementation(libs.retrofit)
    //implementation(libs.gson)
    implementation(libs.gson.converter)
    implementation(libs.androidx.coil)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.layout.android)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.dagger2)
    kapt("com.google.dagger:dagger-compiler:2.55")
    implementation(libs.androidx.pipecat)
    implementation(libs.androidx.accompanist)
    //implementation(libs.androidx.kapt)
    //implementation((libs.androidx.ksp))
    implementation(platform(libs.androidx.firebase))
    implementation(libs.androidx.firebase.analytics)
    implementation(libs.androidx.firebase.vertexai)
    implementation(libs.androidx.firebase.remote.config)
    implementation(libs.androidx.firebase.storage)
    implementation(libs.androidx.firebase.firestore)
    implementation(libs.androidx.firebase.auth)
    implementation(libs.playService)
    implementation(libs.firebase.ui.auth)
    implementation(libs.firebase.appcheck.debug)
    implementation(libs.firebase.appcheck)
    implementation(libs.firebase.perf)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.cloud.messaging)
    implementation(libs.firebase.inapp.messaging)
    implementation(libs.androidx.exoplayer)
    implementation(libs.androidx.exoplayer.dash)
    implementation(libs.androidx.exoplayer.hls)
    implementation(libs.androidx.media3.ui)
    implementation(libs.play.billing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(project(":gcp"))
    implementation("com.composables:core:1.20.0")
    implementation("androidx.compose.ui:ui:1.8.0-alpha04")
    //implementation("androidx.compose.material3:material3:1.4.0-alpha02")
}