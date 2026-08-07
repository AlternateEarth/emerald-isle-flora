# Emerald Isle Flora

A Minecraft mod adding flora inspired by Ireland. Built with
[Stonecraft](https://stonecraft.meza.gg), targeting multiple Minecraft versions and mod
loaders from one codebase:

| Minecraft | Loaders           |
|-----------|-------------------|
| 1.20.1    | Fabric, Forge     |
| 1.21.1    | Fabric, NeoForge  |
| 1.21.11   | Fabric, NeoForge  |
| 26.2      | Fabric, NeoForge  |

(NeoForge doesn't target 1.20.1, and Forge doesn't target anything past it — see
[AGENTS.md](AGENTS.md) if you're curious why.)

> **Not published yet.** This mod isn't on Modrinth/CurseForge/GitHub Releases as of the
> multi-version rewrite — build it yourself for now (see below). Publishing is planned
> for after the next content update.

## Current content

Three decorative flowers, each with the same shape of mechanic:

- **Bells of Ireland** (`emeraldisleflora:bells_of_ireland`) — grants Regeneration when
  used in a suspicious stew. Naturally generates in meadow and plains biomes.
- **Bog Rosemary** (`emeraldisleflora:bog_rosemary`) — grants Nausea in a suspicious
  stew. Naturally generates in swamp and mangrove swamp biomes.
- **Bulbous Buttercup** (`emeraldisleflora:bulbous_buttercup`) — grants Nausea in a
  suspicious stew. Naturally generates in plains, sunflower plains, and meadow biomes.

Each flower:

- Is placeable on grass/dirt, has a potted variant, and is compostable (65% chance;
  95% for the grown variant below).
- Crafts into its matching dye (green/pink/yellow respectively), shapeless, 1:1.
- Has a **grown** variant, reached by using bone meal on a planted (or potted) flower.
  Using bone meal again on an already-grown flower drops an extra flower item without
  reverting the block — a small renewable dye source. Works both by hand and via a
  dispenser loaded with bone meal. Note: mobs can spawn on/inside a grown flower (a
  deliberate override, not an oversight — see AGENTS.md if you're touching
  `GrowableFlower`).

Both flowers and their grown variants are tagged into vanilla's `flowers`/
`small_flowers` tags (block and item), and potted variants into `flower_pots`.

- A config option (`enableGrownFlowerHarvesting`, default `true`) to turn off just the
  repeatable-harvest half of the above — growing a base flower into its grown variant is
  unaffected either way.
- A sample config option (`logStartupMessage`, default `true`) that controls whether the
  mod prints a log line when it finishes loading. Config is saved to
  `config/emeraldisleflora.json`.
- **Mod Menu support** (Fabric, <26.2 only — see below) — if the player has Mod Menu
  installed, an in-game config screen (built with Cloth Config) is available from the
  mod list. Without it, the mod works exactly the same; they just won't see the in-game
  screen, and can still hand-edit the JSON config file. Not available on Forge/NeoForge
  (no Mod Menu equivalent there) or on 26.2 (Cloth Config's current 26.2 build has an
  upstream packaging issue that's incompatible with this project's build tooling) — both
  are known, accepted gaps, not bugs.

Want to add content or otherwise contribute? See [CONTRIBUTING.md](CONTRIBUTING.md). For
the full package layout and coding conventions, see [AGENTS.md](AGENTS.md).

## Requirements

- **JDK 21** to build and run the Gradle tooling itself. Toolchain-managed per target by
  Stonecraft/Loom — you don't need multiple JDKs installed, just 21 on `JAVA_HOME` (or
  configured via `org.gradle.java.home`).
- No local Gradle install needed — this project includes the Gradle Wrapper
  (`gradlew` / `gradlew.bat`), which downloads the exact Gradle version this project
  expects (9.6.1) the first time you run it.

## Building the mod

From the project root:

```bash
# macOS / Linux
./gradlew build

# Windows
gradlew.bat build
```

This builds **every target in the matrix above** in one invocation — Stonecutter fans
`build` out across all 8 chiseled subprojects automatically. To build just one target
(much faster while iterating):

```bash
./gradlew :1.21.11-fabric:build
```

The first run will download Minecraft, mappings, and dependencies for every target, so
it can take a while — subsequent builds are much faster, especially scoped to one
target.

Each target's jar is written to `versions/<target>/build/libs/` (e.g.
`versions/1.21.11-fabric/build/libs/emeraldisleflora-fabric-*.jar`, plus a matching
`-sources.jar`). Copy the jar matching your Minecraft version/loader into that instance's
`mods` folder to install it — you'll also need [Fabric API](https://modrinth.com/mod/fabric-api)
(Fabric targets only) and, on Fabric targets below 26.2, [Cloth
Config](https://modrinth.com/mod/cloth-config). See `versions/dependencies/*.properties`
for the exact dependency versions each target was built against.

## Running a test client

```bash
./gradlew :<target>:runClient   # e.g. ./gradlew :1.21.1-neoforge:runClient
```

This launches into a dedicated, git-ignored `run/` folder inside that target's build
directory, so it won't touch your real Minecraft installation, worlds, or settings.

**Heads up:** Loom's dev-launch (`runClient`) is currently broken or misleading on
several targets in this matrix, for reasons unrelated to this mod's own code (see
AGENTS.md's "Other gotchas" for the current list, and why). A real, installed-instance
test is the actual verification bar this project uses — see the `deployToPrism` Gradle
task in [CONTRIBUTING.md](CONTRIBUTING.md) for the reliable way to get a target's jar
into a real launcher instance for testing.

## Config & Mod Menu

- Config lives in `config/ModConfig.java` (a plain class + Gson, so it works with or
  without Cloth Config/Mod Menu installed) and is saved to
  `config/emeraldisleflora.json` in the run/instance directory.
- The Mod Menu screen is built in `config/ModMenuIntegration.java` (Fabric, <26.2 only —
  see "Current content" above). (Yes, that class lives in the `config` package despite
  being client-only — see AGENTS.md.)
- To test the config screen on a target that has it, run its `runClient` task, open the
  Mods screen from the title/pause menu, find "Emerald Isle Flora", and click the config
  (gear) button.

## Versions this project builds against

`versions/dependencies/<minecraftVersion>.properties` is the authoritative, per-Minecraft-
version source of truth (Fabric Loader/API, mapping set, NeoForge/Forge build, etc. —
Stonecraft reads these directly). A few dependencies aren't pinned per-Minecraft-version
and instead live in the root `gradle.properties`: `cloth_config_version`,
`modmenu_version` (both Fabric-only, and both skipped entirely on 26.2 — see "Current
content" above).

26.2 is the first target with no `yarn_mappings` key in its properties file — Minecraft
ships fully deobfuscated (Mojang's own "Mojmap" names) as of that version, so there's
nothing for Loom to remap. Every earlier target uses community **Yarn** mappings instead.
This matters if you're reading or writing source: see AGENTS.md's Stonecutter section for
how this codebase handles both naming schemes side by side.

## License

MIT — see [LICENSE](LICENSE).
