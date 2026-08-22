import gg.meza.stonecraft.mod
import org.gradle.api.tasks.bundling.AbstractArchiveTask
import org.gradle.language.jvm.tasks.ProcessResources

plugins {
    id("gg.meza.stonecraft")
}

repositories {
    maven("https://maven.terraformersmc.com/") { name = "TerraformersMC" } // Mod Menu
    maven("https://maven.shedaniel.me/") { name = "Shedaniel" } // Cloth Config
}

val hasYarnMappings = project.hasProperty("yarn_mappings")
val hasConfigScreenSupport = hasYarnMappings

// Cloth Config's Yarn build only works on Fabric
dependencies {
    if (hasConfigScreenSupport && mod.isFabric) {
        "modImplementation"("me.shedaniel.cloth:cloth-config-fabric:${project.property("cloth_config_version")}")

        // Mod Menu is an optional/soft dependency
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
    generatedResources = rootProject.layout.projectDirectory.dir(
        if (hasYarnMappings) "src/main/generated/yarn" else "src/main/generated/mojmap"
    )
}

// Recipe in 1.21.1 changed from `item` to `id`
// Recipe in 1.21.11 flattened the ingredient format
val recipeGeneratedResources: String? = when (mod.minecraftVersion) {
    "1.20.1" -> "versions/data/1.20.1/src/main/resources"
    "1.21.1" -> "versions/data/1.21.1/src/main/resources"
    "1.21.11", "26.2" -> "versions/data/1.21.11-plus/src/main/resources"
    else -> null
}

// Loot table folder is singularized in 1.21.1+ (data/minecraft/loot_tables/{blocks,entities,gameplay,items}/*.json)
val lootTableResources: String = if (mod.minecraftVersion == "1.20.1") {
    "versions/data/1.20.1/src/main/resources"
} else {
    "versions/data/1.21.1-plus/src/main/resources"
}

// Tag folder is singularized in 1.21.1+ (data/minecraft/tags/{block,entity,gameplay,item}/*.json)
val tagResources: String = if (mod.minecraftVersion == "1.20.1") {
    "versions/data/1.20.1/src/main/resources"
} else {
    "versions/data/1.21.1-plus/src/main/resources"
}

// 26.2 moved sign rendering onto ordinary blockstate + block-model JSON; this overrides the
// shared, model-less blockstate/model used by every earlier version.
val signBlockAssetResources: String? = if (mod.minecraftVersion == "26.2") {
    "versions/data/26.2-plus/src/main/resources"
} else {
    null
}

// Deal with incompatibility between 26.2+ Mojmap-only targets (no yarn_mappings) and cloth-config-fabric's 26.2 build
val clothConfigDependsLine = if (hasYarnMappings) ",\n\t\t\"cloth-config\": \"*\"" else ""
tasks.named<ProcessResources>("processResources") {
    filesMatching("fabric.mod.json") {
        filter { line -> line.replace("@CLOTH_CONFIG_DEPENDS_LINE@", clothConfigDependsLine) }
    }
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

// recipeGeneratedResources/lootTableResources/tagResources aren't covered by Stonecraft's
// own ForgeLike auto-add (that only applies to modSettings.generatedResources itself), so
// every loader needs them added explicitly here - unlike that block, there's no Fabric/
// ForgeLike split to worry about, since these are plain project directories, not something
// Stonecraft already wires in for one loader family. They do, however, frequently resolve
// to the *same* directory as each other (all three currently split only at the
// 1.20.1/1.21.1+-ish boundaries, just not identically-named boundaries) - e.g. 1.20.1's
// recipeGeneratedResources and lootTableResources are both "versions/data/1.20.1/...".
// Registering the same directory twice as separate srcDirs hits the exact "double-
// registers the same srcDir" sourcesJar duplicate-entry error the comment above warns
// about for generatedResources, just on whichever loader's sourcesJar task doesn't have
// Loom's own dedup (plain Forge/NeoForge, not Fabric's remapSourcesJar) - deduplicated by
// directory value here rather than merging the three into one variable, so each stays
// independently renamable if a future Minecraft version ever splits these at different
// points again.
listOfNotNull(recipeGeneratedResources, lootTableResources, tagResources, signBlockAssetResources).distinct().forEach {
    sourceSets.main {
        resources.srcDir(rootProject.layout.projectDirectory.dir(it))
    }
}

// signBlockAssetResources overrides files at the same path as src/main/resources; sourcesJar
// needs an explicit duplicates strategy for that overlap (processResources handles it fine by default).
tasks.named<Jar>("sourcesJar") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.named<Jar>("jar") {
    from(rootProject.layout.projectDirectory.file("LICENSE")) {
        rename { "${it}_${mod.id}" }
    }
}

// Dev convenience: copy this target's built jar into the matching Prism Launcher
// instance's mods folder. Instance folder names are expected to match
// the chiseled subproject name exactly (e.g. "1.21.11-neoforge").
val prismInstancesDir = (project.findProperty("prismInstancesDir") as String?)
    ?: "/home/Rhonan/.var/app/org.prismlauncher.PrismLauncher/data/PrismLauncher/instances"

tasks.register<Copy>("deployToPrism") {
    group = "deployment"
    description = "Copies the built jar into this target's matching Prism Launcher instance's mods folder."

    val instanceDir = file(prismInstancesDir).resolve(project.name)
    val modsDir = instanceDir.resolve("minecraft/mods")
    // Mojmap-only targets (no yarn_mappings, e.g. 26.2+) have nothing to remap, so Loom
    // doesn't create a remapJar task there - fall back to the plain jar task's output.
    val jarTaskName = if (tasks.names.contains("remapJar")) "remapJar" else "jar"
    val jarTask = tasks.named<AbstractArchiveTask>(jarTaskName)
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

// Changelog for the current version is extracted from the CHANGELOG.md file, if it exists and has an entry for this version.
val changelogForCurrentVersion = run {
    val file = rootProject.layout.projectDirectory.file("CHANGELOG.md").asFile
    if (!file.exists()) return@run "No changelog provided."
    val lines = file.readLines()
    val start = lines.indexOfFirst { it.startsWith("## [${mod.version}]") }
    if (start == -1) return@run "No changelog provided."
    val end = lines.drop(start + 1).indexOfFirst { it.startsWith("## ") }
        .let { if (it == -1) lines.size else start + 1 + it }
    lines.subList(start + 1, end).joinToString("\n").trim()
}

// mod.loader is the lowercase Stonecutter loader id ("fabric"/"forge"/"neoforge").
val loaderDisplayName = when (mod.loader) {
    "neoforge" -> "NeoForge"
    else -> mod.loader.replaceFirstChar { it.uppercase() }
}

publishMods {
    version.set("${mod.version}+${mod.minecraftVersion}-${mod.loader}")
    displayName.set("${mod.name} ${mod.version} ($loaderDisplayName ${mod.minecraftVersion})")
    changelog.set(changelogForCurrentVersion)

    // Only do Modrinth if the environment variables are present, so CI can skip it if the secrets aren't set up.
    if (project.providers.environmentVariable("MODRINTH_TOKEN").isPresent &&
        project.providers.environmentVariable("MODRINTH_ID").isPresent) {
        modrinth {
            // Only require fabric-api if this is a Fabric build.
            if (mod.isFabric) {
                requires("fabric-api")
                // Only require cloth-config and modmenu if this is a Fabric build with config screen support (i.e. Yarn mappings).
                if (hasConfigScreenSupport) {
                    requires("cloth-config")
                    optional("modmenu")
                }
            }
        }
    }
}
