# Emerald Isle Flora

A Fabric mod for Minecraft 1.20.1. Adds flora inspired by Ireland.

## Current content

- **Bells of Ireland** (`emeraldisleflora:bells_of_ireland`) — a decorative flower.
  Placeable on grass/dirt, has a potted variant, is compostable (65% chance), and
  crafts into green dye (shapeless, 1:1). Tagged into vanilla's `flowers` and
  `small_flowers` tags (both block and item).
- **Grown Bells of Ireland** — a bushier variant, reached by using bone meal on a
  planted (or potted) Bells of Ireland. Composts at a higher 95% chance. Using bone
  meal again on an already-grown flower drops an extra flower item without reverting
  the block — a small renewable flower source. Works both by hand and via a dispenser
  loaded with bone meal. Note: mobs can spawn on/inside a grown flower (this is a
  deliberate override, not an oversight — see AGENTS.md if you're touching
  `GrowableFlower`).
- A config option (`enableGrownFlowerHarvesting`, default `true`) to turn off just the
  repeatable-harvest half of the above — growing a base flower into its grown variant
  is unaffected either way.
- A sample config option (`logStartupMessage`, default `true`) that controls whether
  the mod prints a log line when it finishes loading. Config is saved to
  `config/emeraldisleflora.json`.
- **Mod Menu support** (optional/soft dependency) — if the player has Mod Menu
  installed, an in-game config screen (built with Cloth Config) is available from the
  mod list. If they don't have Mod Menu, the mod works exactly the same; they just won't
  see the in-game screen, and can still hand-edit the JSON config file.

Want to add content or otherwise contribute? See [CONTRIBUTING.md](CONTRIBUTING.md).
For the full package layout and coding conventions, see [AGENTS.md](AGENTS.md).

## Requirements

- **JDK 21** to build and run the Gradle tooling itself. This project pins Fabric Loom
  to a recent pre-release (`1.17-SNAPSHOT`, see the versions table below), and Loom
  1.12+ requires JDK 21 + Gradle 9 to run — even though the *compiled mod* still targets
  Java 17 / Minecraft 1.20.1 (that's a separate setting, `sourceCompatibility` /
  `targetCompatibility` in `build.gradle`, unaffected by which JDK runs Gradle).
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

The first run will download Minecraft, mappings, and dependencies, so it can take a few
minutes — subsequent builds are much faster.

The built mod jar is written to `build/libs/emeraldisleflora-*.*.*.jar` (a
`-sources.jar` is also produced alongside it). Copy that jar into a Fabric-loader
instance's `mods` folder to install it like any other mod (you'll also need [Fabric
API](https://modrinth.com/mod/fabric-api) and [Cloth
Config](https://modrinth.com/mod/cloth-config) installed alongside it — see
`gradle.properties` for the exact versions this project was built against).

## Running a test client

```bash
./gradlew runClient
```

This launches into a dedicated `run/` folder inside the project (already git-ignored),
so it won't touch your real Minecraft installation, worlds, or settings. See
[CONTRIBUTING.md](CONTRIBUTING.md) for other run/debug options (dedicated server, IDE
setup, etc.).

## Config & Mod Menu

- Config lives in `config/ModConfig.java` (a plain class + Gson, so it works with or
  without Cloth Config/Mod Menu installed) and is saved to
  `config/emeraldisleflora.json` in the run/instance directory.
- The Mod Menu screen is built in `config/ModMenuIntegration.java`. (Yes, that class
  lives in the `config` package despite being client-only — see AGENTS.md.)
- To test the config screen, run `./gradlew runClient`, open the Mods screen from the
  title/pause menu, find "Emerald Isle Flora", and click the config (gear) button.

## Versions this project is pinned to

See `gradle.properties` for the authoritative list — summarized here for convenience:

| Component     | Version         | Notes                                                                              |
|---------------|-----------------|------------------------------------------------------------------------------------|
| Minecraft     | 1.20.1          |                                                                                    |
| Yarn mappings | 1.20.1+build.10 | Final Yarn build for 1.20.1 (Yarn was retired after 1.21.11)                       |
| Fabric Loader | 0.19.3          | Loader is version-independent; this is the current stable release                  |
| Fabric Loom   | 1.17-SNAPSHOT   |                                                                                    |
| Fabric API    | 0.92.9+1.20.1   | Newest Fabric API release still published for 1.20.1                               |
| Cloth Config  | 11.1.136        |                                                                                    |
| Mod Menu      | 7.2.2           | Final Mod Menu release for 1.20.1                                                  |

## License

MIT — see [LICENSE](LICENSE).
