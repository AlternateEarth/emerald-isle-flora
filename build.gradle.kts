import gg.meza.stonecraft.mod

plugins {
    id("gg.meza.stonecraft")
}

repositories {
    maven("https://maven.terraformersmc.com/") { name = "TerraformersMC" } // Mod Menu
    maven("https://maven.shedaniel.me/") { name = "Shedaniel" } // Cloth Config
}

dependencies {
    if (mod.isFabric) {
        // Cloth Config powers our in-game config screen. It's a real dependency (not
        // optional) since it's what the sample config option's GUI is built from.
        "modImplementation"("me.shedaniel.cloth:cloth-config-fabric:${project.property("cloth_config_version")}")

        // Mod Menu is an optional/soft dependency: the mod works completely fine
        // without it installed. modCompileOnly + modLocalRuntime lets us compile
        // against and test the ModMenu integration in the dev environment
        // (./gradlew runClient) without forcing every player to install Mod Menu.
        "modCompileOnly"("com.terraformersmc:modmenu:${project.property("modmenu_version")}")
        "modLocalRuntime"("com.terraformersmc:modmenu:${project.property("modmenu_version")}")
    }
}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the
    // "build" task if it is present. If you remove this, sources will not be generated.
    withSourcesJar()
}

modSettings {
    // Default is per-chiseled-subproject (versions/<target>/src/main/generated), which
    // would silently split datagen output away from the single, version-controlled
    // src/main/generated tree every other loader/version target also needs to read.
    generatedResources = rootProject.layout.projectDirectory.dir("src/main/generated")
}

// Stonecraft only auto-adds generatedResources to the resources source set for
// ForgeLike targets; Fabric needs it added explicitly or datagen output silently
// drops out of the built jar.
sourceSets.main {
    resources.srcDir(modSettings.generatedResources)
}

tasks.named<Jar>("jar") {
    from(rootProject.layout.projectDirectory.file("LICENSE")) {
        rename { "${it}_${mod.id}" }
    }
}
