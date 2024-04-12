@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.secretsGradlePlugin)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.firebaseCrashlytics)
}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.defaults.properties"
    ignoreList.add("sdk.*")
}

android {
    namespace = "org.mothdigital.stationdistancechecker"
    compileSdk = 34

    buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "org.mothdigital.stationdistancechecker"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
            }
        }

        buildConfigField("String", "BASE_URL", "\"https://koleo.pl/api/v2/main/\"")
        buildConfigField("String", "X_KOLEO_VERSION", "\"1\"")
        buildConfigField("String", "DATABASE_NAME", "\"station_distance_checker_database\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

ksp {
    arg("room.schemaLocation", "${projectDir.absolutePath}/schemas")
}

dependencies {
    implementation(project(":design"))
    implementation(project(":station-span"))
    implementation(project(":station-span:domain"))
    implementation(project(":station-span:data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

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

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.maps.compose)
    implementation(libs.play.services.maps)
}