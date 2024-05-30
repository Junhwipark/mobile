plugins {
    alias(libs.plugins.android.application) // Gradle Version Catalog를 사용하는 경우
    // id 'com.android.application' // 직접 선언할 경우 주석 해제
}

android {
    namespace = "com.example.mobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mobile"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // Gradle Version Catalog를 사용하는 경우
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // 명시적 선언 예시 (Gradle Version Catalog를 사용하지 않는 경우 주석 해제)
    // implementation 'androidx.appcompat:appcompat:1.4.0'
    // implementation 'com.google.android.material:material:1.4.0'
    // implementation 'androidx.activity:activity:1.4.0'
    // implementation 'androidx.constraintlayout:constraintlayout:2.1.2'
    // testImplementation 'junit:junit:4.13.2'
    // androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    // androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    // Glide 라이브러리 추가
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    // Retrofit 라이브러리 추가
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // RecyclerView 라이브러리 추가
    implementation ("androidx.recyclerview:recyclerview:1.2.1")

    // Google Play Services Location API 추가
    implementation ("com.google.android.gms:play-services-location:21.0.1")
}

