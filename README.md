# Emerald Isle Flora

A Fabric mod for Minecraft 1.20.1. Adds flora from and inspired by Ireland.

This is a starter/empty mod: no blocks or items exist yet. It's set up and ready for you
to start adding content. See [AGENTS.md](AGENTS.md) for where things live and how to add
new blocks/items.

## What's already wired up

- **A single source set** (`src/main`) containing both common and client-only code.
  Client-only classes (`client/EmeraldIsleFloraClient`, `client/ModMenuIntegration`) are
  marked with `@Environment(EnvType.CLIENT)` as a hint, but — unlike a
  `splitEnvironmentSourceSets()` project — this isn't compile-time enforced. See
  AGENTS.md for the rule of thumb on keeping client-only code out of common code paths.
- **A pre-registered, currently-empty Creative Inventory tab** (`ModItemGroups`), ready
  for you to add items/blocks to.
- **A sample config option** (`logStartupMessage`, default `true`) that controls whether
  the mod prints a log line when it finishes loading. Config is saved to
  `config/emerald-isle-flora.json`.
- **Mod Menu support** (optional/soft dependency) — if the player has Mod Menu
  installed, an in-game config screen (built with Cloth Config) is available from the
  mod list. If they don't have Mod Menu, the mod works exactly the same; they just won't
  see the in-game screen, and can still hand-edit the JSON config file.

## Requirements

- **JDK 17** to build and run (Minecraft 1.20.1 requires Java 17). Gradle will use
  whatever `JAVA_HOME` points to, or you can point Gradle at a specific JDK — see
  "Using a different JDK" below.
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

The built mod jar is written to `build/libs/emerald-isle-flora-1.0.0.jar` (a
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
Server` run configurations. Use those to run with breakpoints, hot-swap, etc.

## Adding your first block or item

1. Create a registry class, e.g. `registry/ModItems.java` or `registry/ModBlocks.java`,
   following the pattern in `registry/ModItemGroups.java`.
2. Register it, then call your new registration method from
   `EmeraldIsleFlora#onInitialize`.
3. Add it to the creative tab by uncommenting/adding an `entries.add(...)` line inside
   `ModItemGroups#register`.
4. Add a translation key for it to
   `src/main/resources/assets/emerald-isle-flora/lang/en_us.json`, and any textures/
   models under `src/main/resources/assets/emerald-isle-flora/`.

See [AGENTS.md](AGENTS.md) for the full package layout and conventions.

## Config & Mod Menu

- Config lives in `config/ModConfig.java` (a plain class + Gson, so it works with or
  without Cloth Config/Mod Menu installed) and is saved to
  `config/emerald-isle-flora.json` in the run/instance directory.
- The Mod Menu screen is built in `client/ModMenuIntegration.java` using Cloth Config's
  `ConfigBuilder`. Add one new `general.addEntry(...)` block there for every new field
  you add to `ModConfig`.
- To test the config screen, run `./gradlew runClient`, open the Mods screen from the
  title/pause menu, find "Emerald Isle Flora", and click the config (gear) button.

## Versions this project is pinned to

See `gradle.properties` for the authoritative list — summarized here for convenience:

| Component       | Version         | Notes                                                             |
|-----------------|-----------------|-------------------------------------------------------------------|
| Minecraft       | 1.20.1          |                                                                   |
| Yarn mappings   | 1.20.1+build.10 | Final Yarn build for 1.20.1 (Yarn was retired after 1.21.11)      |
| Fabric Loader   | 0.19.3          | Loader is version-independent; this is the current stable release |
| Fabric Loom     | 1.17-SNAPSHOT   | Newest Loom release with support for 1.20.1                       |
| Fabric API      | 0.92.9+1.20.1   | Newest Fabric API release still published for 1.20.1              |
| Cloth Config    | 11.1.136+fabric | Newest Cloth Config release still published for 1.20.1            |
| Mod Menu        | 7.2.2           | Final Mod Menu release for 1.20.1                                 |

### Living on the bleeding edge (optional)

Fabric Loom releases past the 1.10.x line (1.12+) require **Gradle 9** and **JDK 21** to
run the build tooling itself (your compiled mod can still target Java 17/Minecraft
1.20.1 either way — that's a separate setting). This project deliberately pins an older
Loom so `JDK 17` + the bundled Gradle wrapper is all you need out of the box. If you'd
rather use the newest Loom release:

1. Install JDK 21+ and either set `org.gradle.java.home` in `gradle.properties` or make
   sure `JAVA_HOME` points at it.
2. Bump `loom_version` in `gradle.properties`.
3. Bump the `distributionUrl` in `gradle/wrapper/gradle-wrapper.properties` to a Gradle
   9.x release.
4. Check [fabricmc.net/develop](https://fabricmc.net/develop/) for the current
   recommendations.

## License

MIT — see [LICENSE](LICENSE).
