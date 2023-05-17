plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.mapsplatform.secrets.gradle.plugin)
    kotlin("kapt")
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.github.odaridavid.weatherapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.github.odaridavid.weatherapp"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Jetpack Core
    implementation(libs.bundles.androidx)

    // Google Play Services
    implementation(libs.playservices.location)


    // Data & Async
    implementation(libs.retrofit)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization)
    implementation(libs.coil)
    implementation(libs.retrofit.converter.gson)
    // DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    //WorkManager
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)
    kapt(libs.hilt.compiler)

    //Room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    // Test
    testImplementation(libs.junit)
    testImplementation(libs.turbine)
    testImplementation(libs.mock.android)
    testImplementation(libs.mock.agent)
    testImplementation(libs.truth)
    testImplementation(libs.coroutines.test)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
    implementation(libs.androidx.work.testing)
    implementation(libs.core.ktx)
    testImplementation(libs.roboelectric)
    testImplementation(libs.androidx.arch.core)
    implementation(libs.core.ktx.test)

    //Timber
    implementation(libs.timber)

}

kapt {
    correctErrorTypes = true
}
