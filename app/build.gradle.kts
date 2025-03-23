import com.google.protobuf.gradle.id

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.kotlin.plugin.compose")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.protobuf")
}

android {
    namespace = "jp.speakbuddy.edisonandroidexercise"
    compileSdk = 35

    defaultConfig {
        applicationId = "jp.speakbuddy.edisonandroidexercise"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "BASE_URL", "\"https://catfact.ninja/\"")
        buildConfigField("String", "IMAGE_CACHE_DIRECTORY", "\"image_cache\"")
        buildConfigField("String", "FACT_PROTOBUF_FILE_NAME", "\"fact.pb\"")
        buildConfigField( "double", "IMAGE_MEMORY_CACHE_PERCENT", "0.25")
        buildConfigField( "double", "IMAGE_DISK_CACHE_PERCENT", "0.02")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// Setup protobuf configuration, generating lite Java and Kotlin classes

protobuf {
    protoc {
        val protocPlatform = project.findProperty("protoc_platform") as? String
        artifact = if (protocPlatform != null) {
            "com.google.protobuf:protoc:3.25.2:$protocPlatform"
        } else {
            "com.google.protobuf:protoc:3.25.2"
        }
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.54.0"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                val java by registering {
                    option("lite")
                }
                val kotlin by registering {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    val lifecycle_version = "2.8.7"
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.compose.ui:ui:1.7.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.8")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.datastore:datastore-preferences:1.1.3")
    implementation("androidx.datastore:datastore:1.1.3")
    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-android-compiler:2.52")
    implementation("com.google.protobuf:protobuf-kotlin-lite:3.25.2")
    implementation("com.google.protobuf:protoc:3.25.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("io.coil-kt.coil3:coil-compose:3.1.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.1.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.compose.material3.adaptive:adaptive-android:1.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.8")
    debugImplementation("androidx.compose.ui:ui-tooling:1.7.8")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.7.8")
}

kapt {
    correctErrorTypes = true
}