plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.mapsplatform.secrets.gradle.plugin) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization) apply false
//    TODO Move some of these to toml file
    id("com.github.ben-manes.versions") version "0.41.0"
    id("nl.littlerobots.version-catalog-update") version "0.8.4"
    id("io.gitlab.arturbosch.detekt") version "1.23.3"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
//    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
}

buildscript {
    dependencies {
        classpath(libs.com.google.services)
        classpath(libs.com.firebase.crashlytics.plugin)
        classpath("com.github.ben-manes:gradle-versions-plugin:0.51.0")
        classpath("nl.littlerobots.vcu:plugin:0.8.4")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.3")
        classpath("com.android.tools.build:gradle:8.3.0")
    }
}

versionCatalogUpdate {
    pin {
        versions.addAll("kotlin-android", "kotlin-serialization")
    }
}
