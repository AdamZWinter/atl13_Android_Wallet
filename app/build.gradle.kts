plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.alt_13_android_wallet"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.alt_13_android_wallet"
        minSdk = 26
        targetSdk = 33
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

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation ("com.fasterxml.jackson.core:jackson-core:2.15.3")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.15.3")
    implementation ("com.google.zxing:core:3.3.0")
    implementation ("com.google.zxing:android-core:3.3.0")
    implementation ("com.google.zxing:android-integration:3.3.0")
}