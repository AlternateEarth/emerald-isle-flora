# Emerald Isle Flora

A Fabric mod for Minecraft 1.20.1. Adds flora inspired by Ireland.

## Current content

- **Bells of Ireland** (`emeraldisleflora:bells_of_ireland`) — a decorative flower.
  Placeable on grass/dirt, has a potted variant, is compostable (65% chance), and
  crafts into green dye (shapeless, 1:1). Tagged into vanilla's `flowers` and
  `small_flowers` tags (both block and item).
- A sample config option (`logStartupMessage`, default `true`) that controls whether the
  mod prints a log line when it finishes loading. Config is saved to
  `config/emeraldisleflora.json`.
- **Mod Menu support** (optional/soft dependency) — if the player has Mod Menu
  installed, an in-game config screen (built with Cloth Config) is available from the
  mod list. If they don't have Mod Menu, the mod works exactly the same; they just won't
  see the in-game screen, and can still hand-edit the JSON config file.

**Known limitation:** right-clicking an *empty* flower pot with Bells of Ireland does
not currently convert it to the potted block. The potted block exists and renders
correctly, but nothing hooks it into the empty pot's placement logic — that requires a
mixin (Fabric API has no public hook for it), which hasn't been written yet.

See [AGENTS.md](AGENTS.md) for the full package layout and conventions.

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

The built mod jar is written to `build/libs/emeraldisleflora-0.5.0.jar` (a
`-sources.jar` is also produced alongside it). Copy that jar into a Fabric-loader
instance's `mods` folder to install it like any other mod (you'll also need [Fabric
API](https://modrinth.com/mod/fabric-api) and [Cloth
Config](https://modrinth.com/mod/cloth-config) installed alongside it — see
`gradle.properties` for the exact versions this project was built against).

## Running a test client

Loom provides ready-made run configurations, so you can launch a real Minecraft client
with your mod (and its dependencies) already loaded, without exporting/installing
anything:

```bash
./gradlew runClient
```

This launches into a dedicated `run/` folder inside the project (already git-ignored),
so it won't touch your real Minecraft installation, worlds, or settings. The first
launch will download the client jar and assets.

Useful variants:

```bash
# A dedicated server, same idea as above, in the same run/ folder
./gradlew runServer

# Re-generate readable (deobfuscated) Minecraft source, useful for
# "Go to definition" / "Find usages" on vanilla code in your IDE
./gradlew genSources
```

### Running/debugging from an IDE

If you open this project in **IntelliJ IDEA** (recommended — this is what Fabric's
tooling is best tested against) or VS Code with the Java + Gradle extensions, importing
the project via Gradle will automatically create `Minecraft Client` and `Minecraft
Server` run configurations. Use those to run with breakpoints, hot-swap, etc. Make sure
your IDE's project SDK is JDK 21 (see "Requirements" above).

## Adding your first block or item

1. Register it in `registry/ModBlocks.java` (or a new registry class, e.g.
   `ModItems.java`, if you're adding an item that isn't a `BlockItem`), following the
   pattern already there.
2. Call your new registration method from `EmeraldIsleFlora#onInitialize`.
3. Add it to the creative tab inside the `entries()` callback in
   `registry/ModItemGroups.java`.
4. Add a translation key to
   `src/main/resources/assets/emeraldisleflora/lang/en_us.json`, and any textures/models
   under `src/main/resources/assets/emeraldisleflora/`.
5. If it's a cross-shaped block (a flower, sapling, etc.), register it with
   `BlockRenderLayerMap` in `EmeraldIsleFloraClient` — see `BELLS_OF_IRELAND` there for
   the pattern. Skipping this renders the block with an opaque background instead of a
   transparent one.
6. If it should be craftable, compostable, or advancement-tracked like Bells of Ireland,
   add the matching files under `src/main/resources/data/emeraldisleflora/` (recipes,
   advancements) and `src/main/resources/data/minecraft/tags/` (vanilla tag hookups).

See [AGENTS.md](AGENTS.md) for the full package layout and conventions.

## Config & Mod Menu

- Config lives in `config/ModConfig.java` (a plain class + Gson, so it works with or
  without Cloth Config/Mod Menu installed) and is saved to
  `config/emeraldisleflora.json` in the run/instance directory.
- The Mod Menu screen is built in `config/ModMenuIntegration.java` using Cloth Config's
  `ConfigBuilder`. Add one new `general.addEntry(...)` block there for every new field
  you add to `ModConfig`. (Yes, this class lives in the `config` package despite being
  client-only — see AGENTS.md for why that's worth double-checking before you assume
  everything in `config` is safe to call from common code.)
- To test the config screen, run `./gradlew runClient`, open the Mods screen from the
  title/pause menu, find "Emerald Isle Flora", and click the config (gear) button.

## Versions this project is pinned to

See `gradle.properties` for the authoritative list — summarized here for convenience:

| Component     | Version         | Notes                                                                             |
|---------------|-----------------|------------------------------------------------------------------------------------|
| Minecraft     | 1.20.1          |                                                                                    |
| Yarn mappings | 1.20.1+build.10 | Final Yarn build for 1.20.1 (Yarn was retired after 1.21.11)                       |
| Fabric Loader | 0.19.3          | Loader is version-independent; this is the current stable release                 |
| Fabric Loom   | 1.17-SNAPSHOT   | Pre-release, not a fixed version — see "A note on the Loom SNAPSHOT" below         |
| Fabric API    | 0.92.9+1.20.1   | Newest Fabric API release still published for 1.20.1                              |
| Cloth Config  | 11.1.136        |                           |
| Mod Menu      | 7.2.2           | Final Mod Menu release for 1.20.1                                                  |

### A note on the Loom SNAPSHOT

`1.17-SNAPSHOT` is a moving target, not a fixed release — the artifact behind that
version string can change without notice, which risks a build that worked yesterday
failing (or silently behaving differently) today, with no code change on your end. If
you hit an unexplained build issue, checking whether the resolved Loom snapshot changed
underneath you is a reasonable first suspect. Pinning to a fixed Loom release (once
1.17 or later has a non-SNAPSHOT tag) would remove this risk.

## License

MIT — see [LICENSE](LICENSE).