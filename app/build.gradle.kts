plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    kotlin("plugin.serialization") version "1.9.20"

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("/Users/ray/Development/keystore/JavHub.keystore")
            storePassword = "15916679533."
            keyAlias = "jav_hub"
        }
    }
    namespace = "com.jadesoft.javhub"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jadesoft.javhub"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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
        kotlinCompilerExtensionVersion = "1.5.5"
    }
}

dependencies {

    // AndroidX 核心库
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Material Design
    implementation(libs.material)

    // Jetpack Compose 核心依赖
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)

    // Jetpack Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // Jetpack exoplayer
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)

    // Navigation-Compose
    implementation(libs.androidx.navigation.compose)

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)

    // Retrofit + OkHttp
    implementation(libs.retrofit) // Retrofit 核心库
    implementation(libs.retrofit.converter.gson) // Gson 转换器
    implementation(libs.retrofit.converter.scalars) // 字符串转换器
    implementation(libs.okhttp) // OkHttp 核心库
    implementation(libs.okhttp.logging) // OkHttp 日志拦截器

    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
//    implementation(libs.coil.transformations)

    // 协程
    implementation(libs.coroutines.android)

    // 解析html
    implementation(libs.jsoup)

    // 序列化和反序列化
    implementation(libs.kotlinx.serialization.json)

    // 测试依赖
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test)

    // Debug 工具
    debugImplementation(libs.androidx.compose.ui.tooling)

    // zoomable
    implementation(libs.zoomable)

}

kapt {
    correctErrorTypes = true
}