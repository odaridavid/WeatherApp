plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.mapsplatform.secrets.gradle.plugin) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization) apply false
    alias(libs.plugins.nl.littlerobots.versioning) apply false
    alias(libs.plugins.com.github.ben.manes.gradle.versions.plugin) apply false
}

buildscript {
    dependencies {
        classpath(libs.com.google.services)
        classpath(libs.com.firebase.crashlytics.plugin)
        classpath("com.github.ben-manes:gradle-versions-plugin:0.51.0")
        classpath("nl.littlerobots.vcu:plugin:0.8.4")
    }
}
