plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.mapsplatform.secrets.gradle.plugin) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization) apply false
    id("com.github.ben-manes.versions") version "0.41.0"
    id("nl.littlerobots.version-catalog-update") version "0.8.4"
}

buildscript {
    dependencies {
        classpath(libs.com.google.services)
        classpath(libs.com.firebase.crashlytics.plugin)
        classpath("com.github.ben-manes:gradle-versions-plugin:0.51.0")
        classpath("nl.littlerobots.vcu:plugin:0.8.4")
    }
}

versionCatalogUpdate {
    pin {
        versions.addAll("kotlin-android","kotlin-serialization")
    }
}