import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.mapsplatform.secrets.gradle.plugin) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.about.lib.plugin) apply false
    alias(libs.plugins.firebase.perf.plugin) apply false
    alias(libs.plugins.compose.compiler) apply false
//    TODO Move some of these to toml file
    id("com.github.ben-manes.versions") version "0.41.0"
    id("nl.littlerobots.version-catalog-update") version "0.8.4"
    id("io.gitlab.arturbosch.detekt") version "1.23.3"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

buildscript {
    dependencies {
        classpath(libs.com.google.services)
        classpath(libs.com.firebase.crashlytics.plugin)
        classpath(libs.gradle.versions.plugin)
        classpath(libs.littlerobots.plugin)
        classpath(libs.detekt.gradle.plugin)
        classpath(libs.gradle)
    }
}

versionCatalogUpdate {
    pin {
        versions.addAll("kotlin-android")
    }
}

fun isNonStable(version: String): Boolean {
    val nonStableKeyword = listOf("BETA", "ALPHA", "DEV").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = nonStableKeyword.not() || regex.matches(version)
    return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}
