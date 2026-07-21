# AGENTS.md

Guidance for AI coding agents making changes in this repository. Read this
before making changes; it's shorter than re-deriving these conventions from scratch each
session. For the "how do I build/run/open a PR" side of things, see
[CONTRIBUTING.md](CONTRIBUTING.md) instead — this doc stays focused on code structure
and conventions.

## What this project is

A Fabric mod for Minecraft **1.20.1**, adding flora inspired by Ireland. Mod ID is
`emeraldisleflora`; the Java package root is `net.alternateearch.emeraldisleflora`. 
Currently ships Bells of Ireland (a decorative flower) and its bone-meal-grown "bushier" 
variant, with a repeatable harvest mechanic on the grown stage — see README.md's 
"Current content" for the full current feature list. Still a young, actively-growing 
project, not a finished content pack.

Building requires JDK 21 (Fabric Loom 1.17-SNAPSHOT needs it to run), but the compiled
mod still targets Java 17 / Minecraft 1.20.1 at runtime — don't confuse the two when
troubleshooting a build issue. See `README.md` for the full pinned-version table — don't 
assume newer versions than what `gradle.properties` pins without checking whether they 
still support 1.20.1 first (several of these libraries stopped publishing 1.20.1 builds 
once the Fabric ecosystem moved to newer Minecraft versions).

## Repository layout

```
src/main/java/...       All Java code — common AND client-only, in one source set.
src/main/resources/     fabric.mod.json, lang files, textures, models, data.
```

This project uses a **single, unsplit source set** (no
`loom.splitEnvironmentSourceSets()` in `build.gradle`), and client-only code is **not**
grouped into its own package either. There is no `.client` package to look for.
Client-only classes are identified purely by the `@Environment(EnvType.CLIENT)`
annotation and by convention, wherever they happen to live:

- `EmeraldIsleFloraClient` (client-only) lives directly in the root package,
  `net.alternateearch.emeraldisleflora`, alongside the common `EmeraldIsleFlora` class.
- `ModMenuIntegration` (client-only) lives in the `config` package, alongside the
  common `ModConfig` class.

**This means package location tells you nothing about client-vs-common here** — the
`config` package in particular mixes one common class and one client-only class. Check
the `@Environment` annotation and the class's own doc-comment, not which folder it's in,
before assuming something is safe to call from common code. Nothing will fail to
compile if you get this wrong; it will crash a dedicated server at runtime the moment
the bad code path is reached.

Rule of thumb: only Fabric Loader's `"client"` entrypoint
(`EmeraldIsleFloraClient#onInitializeClient`) and Mod Menu's `"modmenu"` entrypoint
(`ModMenuIntegration`) should ever reference client-only classes. Common code
(`EmeraldIsleFlora#onInitialize`, registry classes, block/item classes) must never
import them.

If this project grows enough client-only code that tracking this by convention alone
gets error-prone, reintroducing `loom.splitEnvironmentSourceSets()` (and moving
client-only classes into their own `src/client` source set) would restore compile-time
enforcement — check git history for the last version of this project that did that.

Current package contents:

- `net.alternateearch.emeraldisleflora` — `EmeraldIsleFlora` (main `ModInitializer`;
  loads config, registers item groups/blocks/bone-meal interaction/dispenser behavior,
  exposes the loaded `ModConfig` via a static getter) and `EmeraldIsleFloraClient`
  (client-only; registers `BlockRenderLayerMap` cutout render layers for cross-shaped
  blocks like flowers).
- `net.alternateearch.emeraldisleflora.config` — `ModConfig` (common, a plain
  Gson-backed POJO — add new config fields here) and `ModMenuIntegration` (client-only,
  see above — builds the Cloth Config screen).
- `net.alternateearch.emeraldisleflora.registry` — `ModBlocks` (block instances +
  registration + composting registration), `ModItemGroups` (the creative tab), and
  `GrowableFlower` (a `FlowerBlock` subclass used for the grown variant — currently just
  overrides `canMobSpawnInside` to always return `true`; this is a **deliberate**
  behavior choice, not an oversight — mobs can spawn on/in a grown flower. If you didn't
  intend that when reusing this class for a different flower, override it back).
- `net.alternateearch.emeraldisleflora.util` — the bone-meal grow/harvest mechanic,
  split into `ModCommonLogic` (the shared, config-aware grow-or-harvest logic — the
  single source of truth both entry points below call into), `ModBoneMealInteraction`
  (the by-hand `UseBlockCallback` entry point), and `ModDispenserBehavior` (the
  dispenser entry point, which also reimplements vanilla's bone-meal-dispenser fallback
  — see the convention note below on why that's not optional). All three are common
  code (server-authoritative), not client-only.

Data-driven content lives under two roots, following vanilla's own layout:

- `src/main/resources/assets/emeraldisleflora/` — blockstates, models, textures, lang.
- `src/main/resources/data/emeraldisleflora/` — recipes, advancements, loot tables.
- `src/main/resources/data/minecraft/tags/` — this mod's content added to *vanilla*
  tags (e.g. `blocks/flowers.json`, `items/small_flowers.json`), using `"replace":
  false` so it merges with vanilla's own tag entries instead of overwriting them.

## Conventions to follow when adding content

- **One registry class per content type** (`ModBlocks`, `ModItems`, `ModSounds`, ...),
  under `registry/`, each called from `EmeraldIsleFlora#onInitialize`. Don't scatter
  `Registry.register(...)` calls throughout the codebase.
- **Identifiers**: always `new Identifier(EmeraldIsleFlora.MOD_ID, "snake_case_path")` —
  reuse the `MOD_ID` constant, don't hardcode the string elsewhere.
- **Translation keys**: add an entry to
  `src/main/resources/assets/emeraldisleflora/lang/en_us.json` for every new item,
  block, or config field, in the same PR/commit that adds it. Follow existing key
  patterns (`item.emeraldisleflora.<id>`, `block.emeraldisleflora.<id>`,
  `config.emeraldisleflora.<field>`).
- **Cross-shaped blocks** (flowers, saplings, anything using a `cross` model instead of
  a full cube) need a `BlockRenderLayerMap.INSTANCE.putBlock(...)` call in
  `EmeraldIsleFloraClient`, or they'll render with an opaque background instead of a
  transparent one. Easy to forget since it's a client-only step separate from the block
  registration itself — `BELLS_OF_IRELAND` is the reference example.
- **New creative-tab entries**: add them inside the `entries()` callback in
  `ModItemGroups`, not by creating additional tabs, unless there's a strong reason for a
  second tab.
- **New config fields**: add the field to `ModConfig` (with a sensible default and a
  doc-comment), then add a matching `general.addEntry(...)` block to
  `ModMenuIntegration`, then add its translation keys. All three steps together, please
  — a config field with no GUI entry (or vice versa) is an easy thing to leave half-done.
- **Config-gating a mechanic**: put the check in one shared place, not once per entry
  point. `ModCommonLogic.growOrHarvest` checking `enableGrownFlowerHarvesting` once,
  rather than `ModBoneMealInteraction` and `ModDispenserBehavior` each checking it
  separately, is the reference example — both entry points can't drift out of sync with
  each other if there's only one place the check lives.
- **Registering a behavior for an item/event vanilla already uses** (bone meal is the
  current example, via `DispenserBlock.registerBehavior`): check whether you're
  *replacing* existing behavior rather than adding to it. `DispenserBlock.registerBehavior`
  replaces outright — it is not additive. If so, you must reimplement the vanilla
  fallback for cases that aren't yours (see `ModDispenserBehavior`), or you'll silently
  break that interaction for every other block in the game, not just yours.
- **Recipes/advancements/loot tables**: follow the `green_dye_from_bells_of_ireland`
  files as the reference pattern (a shapeless recipe + a matching advancement that
  unlocks it). When starting a new one from a copy of an existing file (whether from
  this project or another mod entirely), double-check every mod-ID reference inside it
  actually got updated — it's easy to leave a stray reference to whatever project the
  template came from, and JSON data files fail silently (no compile error) rather than
  loudly when an ID is wrong. (This exact mistake has happened in this repo before.)
- **Mixins**: none exist yet. If you need one, create
  `src/main/resources/emeraldisleflora.mixins.json` (and/or a `.client.mixins.json` for
  client-only mixins), reference it from `fabric.mod.json` under a `"mixins"` array, and
  put mixin classes in a `mixin` (or `mixin.client`) subpackage. Prefer Fabric API
  events over mixins whenever an event exists for what you need — the bone-meal
  mechanic uses `UseBlockCallback` and `DispenserBlock.registerBehavior` specifically to
  avoid needing one. (The one known gap that *would* need a mixin here — wiring an
  empty flower pot to auto-convert to `POTTED_BELLS_OF_IRELAND` on right-click — hasn't
  been done; see README.md's "Known limitation".)

## Build & verification commands

```bash
./gradlew build          # Compiles + runs checks; produces build/libs/*.jar
./gradlew runClient      # Launches a real (sandboxed) Minecraft client with this mod loaded
./gradlew runServer      # Launches a dedicated server with this mod loaded
```

Requires JDK 21 on `JAVA_HOME` (or configured via `org.gradle.java.home`) — see
README.md's "Requirements" section for why.

There is no automated test suite yet. `./gradlew build` is the minimum bar before
considering a change done. For anything touching registration, rendering, or
interaction logic, prefer also actually launching `./gradlew runClient` and checking
in-game — Minecraft mod bugs are frequently the kind that only show up at runtime
(missing texture, wrong registry order, client code leaking into common code, an opaque
background on a block that should be transparent, a dispenser behavior that silently
broke a vanilla interaction for unrelated blocks, etc.), not at compile time.

## Things that are easy to get wrong here

- Assuming a class is common code just because it's in a package that doesn't sound
  client-specific. `config/ModMenuIntegration.java` is client-only despite sitting next
  to the common `ModConfig`. Check `@Environment`, not the folder.
- Adding a cross-shaped block without the matching `BlockRenderLayerMap` registration in
  `EmeraldIsleFloraClient` — it'll compile fine and render with a solid background.
- Forgetting to declare a Minecraft-version-appropriate mapping when referencing vanilla
  classes — this project uses **Yarn** mappings (`net.minecraft.*` names), not Mojang's
  official names.
- Adding a hard (`modImplementation`) dependency on Mod Menu. It must stay
  `modCompileOnly` + `modLocalRuntime` in `build.gradle` — Mod Menu is an optional/soft
  dependency by design (see `README.md`).
- Bumping `fabric_version`, `cloth_config_version`, or `modmenu_version` in
  `gradle.properties` to a version that no longer publishes a `1.20.1` build — check the
  version actually lists `1.20.1` support before bumping. Also double-check the *exact*
  version string format matches what's actually published (e.g. Cloth Config's Fabric
  builds are versioned `X.Y.Z+fabric`, with the suffix as part of the version string,
  not a separate qualifier) — `gradle.properties` currently has this one wrong; see
  README.md. This has been flagged more than once now and is still unfixed.
- Copying a recipe/advancement/loot table JSON from elsewhere as a starting point
  without checking every embedded mod-ID reference got updated to this project's ID.
  These files fail silently at runtime (a criterion that never triggers, an advancement
  that never grants) rather than at build time, so a leftover wrong ID is easy to miss.
- Registering a behavior for a vanilla item/event (like `DispenserBlock.registerBehavior`
  for bone meal) without reimplementing the vanilla fallback for cases that aren't
  yours — this silently regresses that interaction for every other block, not just the
  one you're adding.