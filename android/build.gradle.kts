plugins {
    // if edit kt file:
    // id("com.android.library") version "8.1.1" apply true
    // id("org.jetbrains.kotlin.android") version "1.9.10" apply true
    // else if tauri dev:
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.plugin.permissionsx"
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.0")
    implementation("com.google.android.material:material:1.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(project(":tauri-android"))

    // implementation("com.github.getActivity:XXPermissions:18.63")

    // if edit kt file:
    // implementation("androidx.core:core:1.9.0") // 添加 androidx.core 的依赖项
    // implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2") // 添加 com.fasterxml.jackson 的依赖项
}