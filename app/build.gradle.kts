@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.symbolProcessing)
    alias(libs.plugins.safeArgs)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.testLogger)
    alias(libs.plugins.junit5Android)
    alias(libs.plugins.google.gms.google.services)
}
android {
    namespace = "com.se2.students_and_companies"

    compileSdk = 34

    defaultConfig {
        minSdk = 26
        targetSdk = 33
        compileSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    defaultConfig {
        applicationId = "com.students_and_companies"

        versionCode = 1
        versionName = "0.0.1" // SemVer (Major.Minor.Patch)
        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        multiDexEnabled = true

        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }
        named("debug") {
            isDebuggable = true
            // Other debug configuration
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    implementation(projects.studentDashboard)
    implementation(projects.accountProfile)
    implementation(projects.recommendation)
    implementation(projects.studentApplication)
    implementation(libs.firebase.storage)
}
