buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.7.3") // Add the Android Gradle plugin
                classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")
                classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
                // implementation("androidx.core:core-ktx:1.15.0")  削除
                // implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")  削除

                // ... other dependencies (プラグインのみ)
    }
}

plugins {
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("com.google.devtools.ksp") version "2.0.0-Beta1-1.0.15" apply false
}