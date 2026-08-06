pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.kikugie.dev/releases")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.neoforged.net/releases/")
    }
}

plugins {
    id("gg.meza.stonecraft") version "1.12.5"
    id("dev.kikugie.stonecutter") version "0.9.7"
}

stonecutter {
    centralScript = "build.gradle.kts"
    kotlinController = true
    shared {
        fun mc(version: String, vararg loaders: String) {
            // Makes the relevant version directories, e.g. "1.20.1-fabric".
            for (loader in loaders) version("$version-$loader", version)
        }

        mc("1.20.1", "fabric", "forge")

        vcsVersion = "1.20.1-fabric"
    }
    create(rootProject)
}

rootProject.name = "emeraldisleflora"
