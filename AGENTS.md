# AGENTS.md

Guidance for AI coding agents (and humans) making changes in this repository. Read this
before making changes; it's shorter than re-deriving these conventions from scratch each
session.

## What this project is

A Fabric mod for Minecraft **1.20.1** (Java 17), currently just scaffolding — no blocks
or items exist yet. Mod ID is `emerald-isle-flora`; the Java package root is
`net.alternateearch.emeraldisleflora`. See `README.md` for build/run instructions and the
exact pinned dependency versions (Fabric Loader, Loom, Fabric API, Cloth Config, Mod
Menu) — don't assume newer versions than what `gradle.properties` pins without checking
whether they still support 1.20.1 first (several of these libraries stopped publishing
1.20.1 builds once the Fabric ecosystem moved to newer Minecraft versions).

## Repository layout

```
src/main/java/...       All Java code — common AND client-only, in one source set.
src/main/resources/     fabric.mod.json, lang files, textures, models, data.
```

This project uses a **single, unsplit source set** (no
`loom.splitEnvironmentSourceSets()` in `build.gradle`). That means client-only code
(rendering, screens, `MinecraftClient`, Mod Menu, Cloth Config screens, etc.) is *not*
compile-time separated from common code the way it would be in a split-source-set
project — it's on you (or the agent making a change) to keep the boundary correct:

- Client-only classes live under `net.alternateearch.emeraldisleflora.client` and are
  marked `@Environment(EnvType.CLIENT)` as a hint. That annotation does **not** stop you
  from accidentally calling one of these classes from common code — nothing will fail to
  compile if you do, but it **will** crash a dedicated server at runtime the moment that
  code path is reached.
- Rule of thumb: only Fabric Loader's `"client"` entrypoint
  (`EmeraldIsleFloraClient#onInitializeClient`) and Mod Menu's `"modmenu"` entrypoint
  (`ModMenuIntegration`) should ever reference classes in the `client` package. Common
  code (`EmeraldIsleFlora#onInitialize`, registry classes, block/item classes) must never
  import from `net.alternateearch.emeraldisleflora.client`.
- Anything registered (blocks, items, block entities, etc.) belongs in common code
  (outside the `client` package), since registries need to be identical and populated on
  both sides.
- If this project later grows enough client-only code (custom renderers, HUD overlays,
  etc.) that this becomes hard to track by convention alone, re-introducing
  `loom.splitEnvironmentSourceSets()` and moving `client/` back out to its own
  `src/client` source set is the fix — see the git history / previous version of this
  file for the split layout this project used before.

Current package contents:

- `net.alternateearch.emeraldisleflora` — `EmeraldIsleFlora` (main `ModInitializer`),
  exposes the loaded `ModConfig` via a static getter.
- `net.alternateearch.emeraldisleflora.config` — `ModConfig`, a plain Gson-backed POJO.
  Add new config fields here.
- `net.alternateearch.emeraldisleflora.registry` — registration classes. Currently just
  `ModItemGroups` (the empty creative tab). Add `ModItems`, `ModBlocks`,
  `ModBlockEntities`, etc. here as they're needed, one class per registry type, each
  with a `register()` method called from `EmeraldIsleFlora#onInitialize`.
- `net.alternateearch.emeraldisleflora.client` — **client-only.**
  `EmeraldIsleFloraClient` (client `ModInitializer`) and `ModMenuIntegration` (the
  `modmenu` entrypoint, builds the Cloth Config screen). See the rule of thumb above.

## Conventions to follow when adding content

- **One registry class per content type** (`ModItems`, `ModBlocks`, `ModSounds`, ...),
  under `registry/`, each called from `EmeraldIsleFlora#onInitialize`. Don't scatter
  `Registry.register(...)` calls throughout the codebase.
- **Identifiers**: always `new Identifier(EmeraldIsleFlora.MOD_ID, "snake_case_path")` —
  reuse the `MOD_ID` constant, don't hardcode the string elsewhere.
- **Translation keys**: add an entry to
  `src/main/resources/assets/emerald-isle-flora/lang/en_us.json` for every new item,
  block, or config field, in the same PR/commit that adds it. Follow existing key
  patterns (`item.emerald-isle-flora.<id>`, `block.emerald-isle-flora.<id>`,
  `config.emerald-isle-flora.<field>`).
- **New creative-tab entries**: add them inside the `entries()` callback in
  `ModItemGroups`, not by creating additional tabs, unless there's a strong reason for a
  second tab.
- **New config fields**: add the field to `ModConfig` (with a sensible default and a
  doc-comment), then add a matching `general.addEntry(...)` block to
  `ModMenuIntegration`, then add its translation keys. All three steps together, please
  — a config field with no GUI entry (or vice versa) is an easy thing to leave half-done.
- **Mixins**: none exist yet. If you need one, create
  `src/main/resources/emerald-isle-flora.mixins.json` (and/or a
  `.client.mixins.json` for client-only mixins), reference it from `fabric.mod.json`
  under a `"mixins"` array, and put mixin classes in a `mixin` (or `mixin.client`)
  subpackage. Prefer Fabric API events over mixins whenever an event exists for what you
  need.

## Build & verification commands

```bash
./gradlew build          # Compiles + runs checks; produces build/libs/*.jar
./gradlew runClient      # Launches a real (sandboxed) Minecraft client with this mod loaded
./gradlew runServer      # Launches a dedicated server with this mod loaded
```

There is no automated test suite yet. `./gradlew build` is the minimum bar before
considering a change done. For anything
touching registration, rendering, or the config screen, prefer also actually launching
`./gradlew runClient` and checking in-game — Minecraft mod bugs are frequently the kind
that only show up at runtime (missing texture, wrong registry order, client code leaking
into common code, etc.), not at compile time.

## Things that are easy to get wrong here

- Referencing a class from `net.alternateearch.emeraldisleflora.client` (or any other
  client-only API like `MinecraftClient`) from common code. Nothing will fail to compile
  — this project doesn't use split source sets — but it will crash a dedicated server at
  runtime. See "Repository layout" above.
- Forgetting to declare a Minecraft-version-appropriate mapping when referencing vanilla
  classes — this project uses **Yarn** mappings (`net.minecraft.*` names), not Mojang's
  official names.
- Adding a hard (`modImplementation`) dependency on Mod Menu. It must stay
  `modCompileOnly` + `modLocalRuntime` in `build.gradle` — Mod Menu is an optional/soft
  dependency by design (see `README.md`).
- Bumping `fabric_version`, `cloth_config_version`, or `modmenu_version` in
  `gradle.properties` to a version that no longer publishes a `1.20.1` build. Check the
  version actually lists `1.20.1` support before bumping.
