plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.demo"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.demo"
        minSdk = 24
        targetSdk = 36
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    
    // Retrofit - HTTP 客户端
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    
    // OkHttp - HTTP 客户端基础库
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    
    // Glide - 图片加载库
    implementation(libs.glide)
    
    // ViewModel 和 LiveData
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    
    // Coroutines
    implementation(libs.coroutines)
    
    // RecyclerView
    implementation(libs.recyclerview)
    implementation(libs.swiperefreshlayout)
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}