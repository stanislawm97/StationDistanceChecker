@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = "org.mothdigital.station_span.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {
    implementation(project(":station-span:domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    testImplementation(libs.room.testing)

    testImplementation(libs.junit)
    testImplementation(libs.koin.test.junit4)
    testImplementation(libs.jupiter)
    testImplementation(libs.jupiter.api)
    testImplementation(libs.jupiter.params)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}