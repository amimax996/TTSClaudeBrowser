pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven("https://plugins.gradle.org/m2/") // Hilt プラグインのリポジトリを追加
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    // バージョンカタログの設定を追加
    versionCatalogs {
        create("libs") {
            // ライブラリのバージョンを定義
            version("kotlin", "1.8.0") // 例: Kotlinのバージョン
            version("androidx-core", "1.9.0") // 例: androidx.coreのバージョン

            // ライブラリの依存関係を定義
            library("kotlin-stdlib", "org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
            withoutVersion() // バージョン不要を宣言する場合
            library("androidx-core-ktx", "androidx.core", "core-ktx")

            // プラグインのバージョンを定義
            plugin("android-application", "com.android.application")
            plugin("kotlin-android", "org.jetbrains.kotlin.android")

        }
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            mavenCentral()
        }
    }

    rootProject.name = "TTSClaudeBrowser"
    include(":app")
}