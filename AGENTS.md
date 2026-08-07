# AGENTS.md

Guidance for AI coding agents making changes in this repository. Read this
before making changes; it's shorter than re-deriving these conventions from scratch each
session. For the "how do I build/run/open a PR" side of things, see
[CONTRIBUTING.md](CONTRIBUTING.md) instead — this doc stays focused on code structure
and conventions.

## What this project is

A Minecraft mod adding flora inspired by Ireland, built with
[Stonecraft](https://stonecraft.meza.gg) (which wires
[Stonecutter](https://stonecutter.kikugie.dev/) for multi-version and
[Architectury Loom](https://docs.architectury.dev/) for multi-loader) to target 8
Minecraft-version × loader combinations from **one** copy of the source, not 8 separate
module trees:

| Minecraft | Loaders          |
|-----------|------------------|
| 1.20.1    | Fabric, Forge    |
| 1.21.1    | Fabric, NeoForge |
| 1.21.11   | Fabric, NeoForge |
| 26.2      | Fabric, NeoForge |

Mod ID is `emeraldisleflora`; the Java package root is
`net.alternateearth.emeraldisleflora`. Currently ships three flowers (Bells of Ireland,
Bog Rosemary, Bulbous Buttercup) each with a bone-meal-grown variant and a repeatable
harvest mechanic — see README.md's "Current content" for the full player-facing feature
list. Still a young, actively-growing project, not a finished content pack.

Building requires JDK 21 across the whole matrix (Loom 1.17-SNAPSHOT needs it to run) —
see `README.md`'s "Requirements" for details.

## How this repo is actually structured (read this before touching anything)

This is **not** a classic Architectury `common`/`fabric`/`forge` split with separate
module source sets. There is exactly **one** copy of the source
(`src/main/java`, `src/main/resources`), written using every Minecraft-version/loader
name it will ever need to compile as, gated behind Stonecutter comment directives. At
build time, Stonecutter *chisels* that one source tree into 8 separate, fully-resolved
copies under `versions/<target>/build/generated/stonecutter/`, one per row above (e.g.
`versions/1.21.11-fabric/`), and Loom builds each independently.

**When editing, always edit the root `src/main/java`/`src/main/resources` tree, never
anything under `versions/`** — the latter is fully regenerated on every build and any
edits there are silently discarded.

### The Stonecutter comment syntax

```java
/*? if <1.21 {*/
import net.minecraft.block.FlowerBlock;
/*?} else {*/
/*import net.minecraft.world.level.block.FlowerBlock;*/
/*?}*/
```

The **active** branch for a given target is left as plain, real Java; every other branch
is wrapped in a real `/* */` block comment. Stonecutter rewrites which branch is active
per target before compiling. Conditions can test the Minecraft version
(`<1.21`, `>=1.21.11`, `26.2`, chained with `&&`) and/or the loader
(`fabric`, `forge`, `neoforge`, or `forgeLike` for "either Forge or NeoForge" — shared
code that's identical across both). `else if` chains work too (see `GrowableFlower.java`
for a real 3-way example).

**Two hard-won gotchas, both real bugs hit during this project's version migration:**

1. **Version comparisons are raw numeric, not semantic-aware.** `26.2 >= 1.21.11` is
   `true` (26 > 1 as the first component), so a bare `/*? if >=1.21.11 {*/` block written
   before 26.2 existed will silently also match 26.2 and pull in stale, wrong-mapping
   code. If you're adding a new Minecraft-version target, **grep the whole codebase for
   every existing bare `>=`/`<` version condition** and check whether the new version
   should also satisfy it — bound it explicitly (`>=1.21.11 && <26.2`) or restructure
   into a proper multi-way chain if not.
2. **A `/*? ... ?*/` marker nested inside an already-*disabled* (real, wrapped)
   comment block is never seen by Stonecutter's scanner** — and worse, since a real Java
   block comment can't nest, an inner `/*` inside one prematurely closes the outer
   comment, corrupting the file. This bites whenever a whole method/field is
   wrapped as one big `/*? if X {*/ ... /*?}*/` block (common for loader-specific
   methods that don't exist at all on other loaders) and you need to add a *second*,
   version-based split inside it. Nesting is completely safe when the *outer* scope is
   **active** (plain, uncommented code) — only breaks when nesting inside an already-
   disabled one. Where that comes up, use two **sibling** blocks instead of nesting:
   `/*? if neoforge && <26.2 {*/ ...body A... /*?}*/` followed by
   `/*? if neoforge && >=26.2 {*/ ...body B... /*?}*/`, rather than one `neoforge` block
   with a nested version check inside.
3. A related trap: a bare `//` line comment placed *immediately* after a `{*/` or
   `else {*/` marker, before the real code's own `/*` wrapper opens, corrupts the
   generated file for whichever branch needs unwrapping — keep explanatory comments
   *inside* the `/* ... */` wrapper, not straddling the boundary.

### Yarn vs. Mojmap — the other axis of divergence

Every target through 1.21.11 uses community **Yarn** mappings
(`net.minecraft.block.FlowerBlock`-style names). **26.2 ships fully deobfuscated** —
Mojang's own official ("Mojmap") names, in different packages
(`net.minecraft.world.level.block.FlowerBlock`) and often different method/field names
too, not just different packages (`Identifier.of(...)` → `Identifier.fromNamespaceAndPath(...)`;
Mojmap's own `Registries` class holds registry **key constants**, the *opposite* of what
Yarn's identically-named `Registries` class holds — Yarn's `Registries` is Mojmap's
`BuiltInRegistries`). This is why almost every version-conditional in this codebase is
ultimately gated on `<26.2`/`>=26.2` somewhere in its chain, not just on the specific
API-shape versions. **Never assume a Yarn-convention name/rename applies to Mojmap, or
vice versa — verify against the real jar.** `javap -classpath <jar> -p <ClassName>`
against the real Minecraft/loader/Fabric-API jars for the target in question is the
standard way this codebase's own version-porting work was done; trust that over memory
or convention, especially for 26.2 where there's no online mapping-diff tooling to lean
on yet.

### Repository layout

```
src/main/java/...              All Java code — common AND client-only, in one source set,
                                Stonecutter-conditioned for all 8 targets at once.
src/main/resources/            fabric.mod.json, mods.toml/neoforge.mods.toml, lang files,
                                textures, models, data - shared across all targets. A few
                                files are further templated with ${...}/@TOKEN@ tokens
                                resolved per-target by build.gradle.kts (see its
                                dependencies{}/processResources blocks).
src/main/generated/            Checked-in datagen output (worldgen configured/placed
                                features) for Yarn targets (<26.2).
src/main/generated-mojmap/     The same, but Mojmap-schema, for 26.2+ - genuinely
                                different JSON shape, not just different values (Mojang
                                restructured worldgen feature configs for 26.2 - see
                                ModConfiguredFeatures/ModPlacedFeatures). Regenerate via
                                `./gradlew :<fabric-target>:runDatagen` from a target on
                                the matching side of the split - see CONTRIBUTING.md.
versions/<target>/             Per-target chiseled output + build directory (generated,
                                gitignored, never edit here).
versions/dependencies/         One <minecraftVersion>.properties per row of the matrix -
                                Fabric Loader/API version, mapping set (or its absence,
                                for 26.2+), NeoForge/Forge build. Authoritative source of
                                truth for what each target actually builds against.
```

Same as before this project adopted Stonecraft: this project uses a **single, unsplit
source set** (no `loom.splitEnvironmentSourceSets()`), and client-only code is **not**
grouped into its own package either. There is no `.client` package to look for.
Client-only classes are identified purely by the `@Environment(EnvType.CLIENT)`
annotation and by convention, wherever they happen to live:

- `EmeraldIsleFloraClient` (client-only) lives directly in the root package,
  `net.alternateearth.emeraldisleflora`, alongside the common `EmeraldIsleFlora` class.
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

If client-only code grows enough that tracking this by convention gets error-prone,
consider reintroducing `loom.splitEnvironmentSourceSets()` (moving client-only classes
into their own `src/client` source set) to restore compile-time enforcement.

Current package contents:

- `net.alternateearth.emeraldisleflora` — `EmeraldIsleFlora` (main entrypoint, loader-
  conditioned for Fabric/Forge/NeoForge; loads config, registers item groups/blocks/
  bone-meal interaction/dispenser behavior/world-gen), `EmeraldIsleFloraClient`
  (client-only, Fabric-only; on `<26.2` registers cutout render layers for cross-shaped
  blocks like flowers via `BlockRenderLayerMap` - as of 26.1+ this is no longer needed at
  all, vanilla auto-detects cutout from the texture's own transparency, see the class's
  own doc-comment), and `EmeraldIsleFloraDataGenerator` (Fabric-only, drives the checked-
  in `src/main/generated*` output).
- `net.alternateearth.emeraldisleflora.config` — `ModConfig` (common, a plain
  Gson-backed POJO — add new config fields here) and `ModMenuIntegration` (client-only,
  Fabric + `<26.2` only, see above — builds the Cloth Config screen).
- `net.alternateearth.emeraldisleflora.registry` — `ModBlocks` (block instances +
  registration + composting registration), `ModItemGroups` (the creative tab entries),
  `GrowableFlower` (a `FlowerBlock` subclass used for every grown variant — currently
  just overrides the "can a mob spawn on this block" hook to always return `true`; this
  is a **deliberate** behavior choice, not an oversight — mobs can spawn on/in a grown
  flower. If you didn't intend that when reusing this class for a different flower,
  override it back), and `ModConfiguredFeatures`/`ModPlacedFeatures` (natural-worldgen
  registration for all three flowers, consumed both by the checked-in datagen JSON at
  runtime and by `ModWorldGen`'s Fabric biome-injection below).
- `net.alternateearth.emeraldisleflora.data` — `ModWorldGenerator` (Fabric-only datagen
  provider; serializes `ModConfiguredFeatures`/`ModPlacedFeatures` into the checked-in
  JSON under `src/main/generated*`).
- `net.alternateearth.emeraldisleflora.util` — the bone-meal grow/harvest mechanic,
  split into `ModCommonLogic` (the shared, config-aware grow-or-harvest logic — the
  single source of truth both entry points below call into), `ModBoneMealInteraction`
  (the by-hand entry point — a Fabric `UseBlockCallback` on Fabric, a
  `PlayerInteractEvent.RightClickBlock` listener on Forge/NeoForge), and
  `ModDispenserBehavior` (the dispenser entry point, which also reimplements vanilla's
  bone-meal-dispenser fallback — see the convention note below on why that's not
  optional). All four are common code (server-authoritative), not client-only.
- `ModWorldGen` (in `registry`, Fabric-only) — injects the placed features above into
  vanilla biomes via `BiomeModifications`. Forge/NeoForge do the equivalent
  declaratively instead, via `data/emeraldisleflora/<forge|neoforge>/biome_modifier/`
  JSON, so this class is a no-op there (called uniformly from every loader's entrypoint
  regardless, for simplicity).

Data-driven content lives under two roots, following vanilla's own layout:

- `src/main/resources/assets/emeraldisleflora/` — blockstates, models, textures, lang.
- `src/main/resources/data/emeraldisleflora/` — recipes, advancements, loot tables, and
  the Forge/NeoForge declarative biome-modifier JSON mentioned above.
- `src/main/resources/data/minecraft/tags/` — this mod's content added to *vanilla*
  tags (e.g. `blocks/flowers.json`, `items/small_flowers.json`, `blocks/flower_pots.json`),
  using `"replace": false` so it merges with vanilla's own tag entries instead of
  overwriting them.

## Conventions to follow when adding content

- **One registry class per content type** (`ModBlocks`, `ModItems`, `ModSounds`, ...),
  under `registry/`, each called from every loader's entrypoint (`EmeraldIsleFlora`'s
  Fabric `onInitialize`, and its Forge/NeoForge `onRegister` event handlers). Don't
  scatter `Registry.register(...)` calls throughout the codebase.
- **Identifiers**: reuse the `MOD_ID` constant, don't hardcode the string elsewhere.
  `Identifier.of(EmeraldIsleFlora.MOD_ID, "snake_case_path")` on Yarn targets (`<26.2`),
  `Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "snake_case_path")` on
  Mojmap (`>=26.2`) — see the Yarn-vs-Mojmap section above.
- **Translation keys**: add an entry to
  `src/main/resources/assets/emeraldisleflora/lang/en_us.json` for every new item,
  block, or config field, in the same PR/commit that adds it. Follow existing key
  patterns (`item.emeraldisleflora.<id>`, `block.emeraldisleflora.<id>`,
  `config.emeraldisleflora.<field>`). Note 1.21.11+'s item-translation-key default
  changed (see "Other gotchas") — new items need `.setId(...)`/`useBlockDescriptionPrefix()`
  (Mojmap) or `.registryKey(...)`/`.useBlockPrefixedTranslationKey()` (Yarn ≥1.21.11) if
  they should delegate to the block's own lang entry rather than needing a separate one.
- **Cross-shaped blocks** (flowers, saplings, anything using a `cross` model instead of
  a full cube): on Fabric targets `<26.2`, need a `putCutout(...)`-style
  `BlockRenderLayerMap` call in `EmeraldIsleFloraClient` (see `BELLS_OF_IRELAND` there for
  the pattern), or they'll render with an opaque background instead of a transparent one.
  Forge/NeoForge read a `"render_type": "cutout"` key from the block model JSON instead
  (see `assets/emeraldisleflora/models/block/`) and never need Java for this. **On
  26.2+, neither is needed at all** — vanilla auto-assigns the render layer from the
  texture's own pixel transparency.
- **New creative-tab entries**: add them inside `ModItemGroups`'s existing
  entries-callback, not by creating additional tabs, unless there's a strong reason for a
  second tab.
- **New config fields**: add the field to `ModConfig` (with a sensible default and a
  doc-comment), then add a matching `general.addEntry(...)` block to
  `ModMenuIntegration`, then add its translation keys. All three steps together, please
  — a config field with no GUI entry (or vice versa) is an easy thing to leave half-done.
- **Config-gating a mechanic**: put the check in one shared place, not once per entry
  point, so entry points can't drift out of sync. `ModCommonLogic.growOrHarvest`
  checking `enableGrownFlowerHarvesting` once, rather than `ModBoneMealInteraction` and
  `ModDispenserBehavior` each checking it separately, is the reference example.
- **Registering a behavior for an item/event vanilla already uses** (bone meal is the
  current example, via `DispenserBlock.registerBehavior`, which replaces outright rather
  than adding to existing behavior): reimplement the vanilla fallback for cases that
  aren't yours (see `ModDispenserBehavior`), or you'll silently break that interaction
  for every other block in the game, not just yours.
- **Recipes/advancements/loot tables**: follow one of the three existing flowers' files
  as the reference pattern (a shapeless dye recipe + a matching advancement that unlocks
  it, plus a bone-meal-growth advancement). When copying an existing file as a starting
  point, double-check every mod-ID reference got updated — JSON data files fail silently
  (no compile error) rather than loudly when an ID is wrong, and this exact mistake has
  happened in this repo before.
- **Natural worldgen**: add the configured/placed feature to `ModConfiguredFeatures`/
  `ModPlacedFeatures`, wire the biome injection into `ModWorldGen` (Fabric) *and* the
  matching declarative JSON under `data/emeraldisleflora/{forge,neoforge}/biome_modifier/`,
  then regenerate the checked-in datagen JSON (see CONTRIBUTING.md) — all data-consuming
  targets read the checked-in JSON at runtime, not the bootstrap methods directly, so a
  stale/missing regen is a silent, no-compile-error content bug.
- **Mixins**: none exist yet. If you need one, create
  `src/main/resources/emeraldisleflora.mixins.json` (and/or a `.client.mixins.json` for
  client-only mixins), reference it from `fabric.mod.json` under a `"mixins"` array, and
  put mixin classes in a `mixin` (or `mixin.client`) subpackage. Prefer Fabric API
  events over mixins whenever an event exists for what you need — the bone-meal
  mechanic uses `UseBlockCallback` and `DispenserBlock.registerBehavior` specifically to
  avoid needing one.

## Build & verification commands

```bash
./gradlew build                        # Builds every target in the matrix
./gradlew :1.21.11-fabric:build         # Builds just one target (much faster)
./gradlew :1.21.11-fabric:runClient     # Dev-launch client for one target
./gradlew :1.21.11-fabric:deployToPrism # Copies that target's jar into a real Prism
                                         # Launcher instance's mods folder, if one exists
                                         # at the configured path (see build.gradle.kts)
./gradlew deployToPrism                 # Same, for every target with a matching instance
```

There is no automated test suite. `./gradlew build` (the whole matrix) is the minimum
bar before considering a change done.

**For anything touching registration, rendering, or interaction logic, a real-install
test via `deployToPrism` is the actual verification bar this project uses, not
`runClient`.** Loom's dev-launch is currently broken or actively misleading on several
targets in this matrix for reasons unrelated to this mod's own code (see "Other
gotchas") — real, previously-shipped bugs in this codebase (a `NoClassDefFoundError`
crash, broken cutout rendering, a flower-pot placement bug, a real-install-only registry
crash) were only ever caught by testing a real installed instance, never by `runClient`
or a green `./gradlew build` alone.

## Other gotchas

- Assuming a vanilla API rename/removal applies uniformly across the whole matrix
  without checking. This codebase has hit multiple real cases of a rename happening a
  *second* time between two targets that both already looked "past" the original change
  (e.g. `Item.Properties#useBlockPrefixedTranslationKey()`, introduced at 1.21.11, was
  itself renamed again to `useBlockDescriptionPrefix()` for 26.2's Mojmap names — a
  silent, non-crashing regression if assumed unchanged). Verify against the real jar
  (`javap`) for the specific target, don't extrapolate from a nearby version.
- Adding a hard (`modImplementation`) dependency on Mod Menu. It must stay
  `modCompileOnly` + `modLocalRuntime` — Mod Menu is an optional/soft dependency by
  design (see `README.md`).
- Assuming Cloth Config/Mod Menu are available on every Fabric target. Both are skipped
  entirely on `>=26.2` (`hasConfigScreenSupport` in `build.gradle.kts`) — Cloth Config's
  currently-published 26.2 build ships an access-widener incompatible with Loom's
  no-remap pipeline for Mojmap-only targets, a real upstream gap, not something fixable
  here. `fabric.mod.json`'s `cloth-config` dependency is conditionally templated for the
  same reason — see its `@CLOTH_CONFIG_DEPENDS_LINE@` token and the matching
  `processResources` filter in `build.gradle.kts` before assuming that file is static.
- Bumping a per-target dependency version (in `versions/dependencies/*.properties`) or a
  shared one (`cloth_config_version`, `modmenu_version` in `gradle.properties`) without
  checking it still publishes a build for that specific Minecraft version — several of
  these libraries have version lines that stop at a given Minecraft version. Also
  double-check the *exact* version string format matches what's actually published (e.g.
  Cloth Config's Fabric builds are versioned `X.Y.Z+fabric`, with the suffix as part of
  the version string, not a separate qualifier).
- Known, currently-accepted `runClient` (Loom dev-launch) failures, none of them this
  mod's own bug (confirmed via `./gradlew build` staying green and real-install testing
  working): `1.20.1-forge`, `1.21.1-fabric`, and `1.21.11-fabric` all crash before this
  mod's code even runs, due to pre-existing bugs in Forge's/Fabric API's own dev-launch
  environment on this Minecraft-version line. Don't spend time debugging "why doesn't
  `runClient` work" on those specific targets as if it were a regression in this repo —
  it isn't; use `deployToPrism` instead.
- Running datagen across multiple **same-mapping-scheme** Fabric targets at once (e.g.
  `chiseledDatagen`, or two Yarn-side `runDatagen` invocations together). They write to
  the *same* shared `src/main/generated` directory and race on Fabric's own stale-file-
  cleanup tracking cache — confirmed to actually delete the correct, just-regenerated
  committed files when run in parallel (`org.gradle.parallel=true` is on for this
  project). Regenerate one target per side of the Yarn/Mojmap split, not the whole
  matrix — see CONTRIBUTING.md.
