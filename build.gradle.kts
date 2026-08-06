import gg.meza.stonecraft.mod
import org.gradle.api.tasks.bundling.AbstractArchiveTask

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
// ForgeLike targets (see Java.kt); Fabric needs it added explicitly or datagen output
// silently drops out of the built jar. Doing this unconditionally for ForgeLike too
// double-registers the same srcDir and breaks sourcesJar with a duplicate-entry error.
if (mod.isFabric) {
    sourceSets.main {
        resources.srcDir(modSettings.generatedResources)
    }
}

tasks.named<Jar>("jar") {
    from(rootProject.layout.projectDirectory.file("LICENSE")) {
        rename { "${it}_${mod.id}" }
    }
}

// Dev convenience: copy this target's built jar into the matching Prism Launcher
// instance's mods folder, for real-install testing outside Loom's dev-launch
// environment (which has known bugs, unrelated to this mod, on some targets - see the
// migration plan doc under .agents/plans/). Instance folder names are expected to match
// the chiseled subproject name exactly (e.g. "1.21.11-neoforge"). Override
// prismInstancesDir (gradle.properties or -PprismInstancesDir=...) if your Prism data
// directory differs; targets without a matching instance are skipped, not failed, so
// this stays safe to run across the whole matrix at once.
val prismInstancesDir = (project.findProperty("prismInstancesDir") as String?)
    ?: "/home/Rhonan/.var/app/org.prismlauncher.PrismLauncher/data/PrismLauncher/instances"

tasks.register<Copy>("deployToPrism") {
    group = "deployment"
    description = "Copies the built jar into this target's matching Prism Launcher instance's mods folder."

    val instanceDir = file(prismInstancesDir).resolve(project.name)
    val modsDir = instanceDir.resolve("minecraft/mods")
    val jarTask = tasks.named<AbstractArchiveTask>("remapJar")
    dependsOn(jarTask)

    onlyIf {
        val exists = instanceDir.exists()
        if (!exists) logger.warn("deployToPrism: no Prism instance '${project.name}' at $instanceDir, skipping")
        exists
    }

    doFirst {
        modsDir.mkdirs()
        modsDir.listFiles { f -> f.name.startsWith("${mod.id}-") && f.name.endsWith(".jar") }
            ?.forEach { it.delete() }
    }

    from(jarTask.flatMap { it.archiveFile })
    into(modsDir)
}
