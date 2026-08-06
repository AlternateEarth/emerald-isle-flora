# Migrating emeraldisleflora to Stonecraft (multi-version, multi-loader)

## Progress (updated 2026-08-06 — read this first when resuming)

Branch: `feat/multi-version`. Working tree clean; every stage below lands as its own
commit, already pushed to no remote (local only so far — nothing has been pushed).

- **Stage 0 — done** (`eb29876`). Toolchain was already on JDK 21 / Gradle 9.6.1 /
  Loom 1.17-SNAPSHOT before this branch started; only fix needed was a stale comment in
  `gradle.properties`.
- **Stage 1 — done** (`aaefde3`). Stonecraft/Stonecutter scaffolding, single target
  `1.20.1-fabric`. Verified in-game (registration, rendering, bone-meal, world-gen all
  confirmed working by the user). Two real bugs found and fixed: LICENSE silently
  dropped from the jar (chiseled subproject's `projectDir` isn't root), and generated
  world-gen JSON silently dropped from the jar (Stonecraft's default
  `generatedResources` dir is per-chiseled-subproject, not root `src/main/generated`).
- **Stage 2 — done and fully verified** (`d5d8e3a`, `2113b9d`). Forge added for 1.20.1.
  Turned out much bigger than the original plan assumed — see the rewritten Stage 2
  section below and its "Actual outcome" note. `1.20.1-fabric` reverified in-game with
  no regressions.

  `./gradlew :1.20.1-forge:runClient` (Architectury Loom's dev-launch task) remains
  broken - still worth fixing eventually for developer convenience, but no longer
  blocking, since the mod itself has now been fully verified through a **real** Forge
  1.20.1 (build 47.4.10) install via Prism Launcher instead: flowers spawned, rendered,
  and the game ran cleanly with zero errors. Getting there took two real, now-fixed
  bugs, both discovered only by testing outside Loom's dev environment:
  1. `mods.toml`'s `[[dependencies.X]]` blocks were missing a `mandatory = true` field -
     Forge 47.4.10 rejects any dependency block without it
     (`InvalidModFileException: Missing required field mandatory in dependency (...)`).
     `type = "required"` alone (the older schema, and what Stonecraft's own e2e testmod
     example uses) is no longer sufficient on this Forge patch line. Fixed in `2113b9d`.
  2. A Flatpak NVIDIA GL driver version mismatch on the tester's machine (unrelated to
     this repo, fixed with `flatpak update`) was blocking the graphics window entirely
     before mod code even ran.

  In hindsight, bug 1 almost certainly *also* explains the original
  `:1.20.1-forge:runClient` crash under Loom (`"Missing required field mandatory in
  dependency (main)"` - Forge's own synthetic system-mod descriptor hitting the same
  validation rule) - so that Loom failure may not be a pure dev-environment bug after
  all. Worth re-testing `runClient` if picking up the Loom dev-launch investigation
  again; low priority since the real-install path is now a proven working alternative.
- **Matrix corrected**: NeoForge dropped from 1.20.1 entirely (confirmed infeasible —
  see the matrix section). 9 targets → 8.
- **Next up: Stage 3** (1.21.1, Fabric + NeoForge) — not started. First new Minecraft
  version (real vanilla API deltas expected) and NeoForge's first appearance in the
  matrix (first time since the 1.20.1-neoforge failure, so worth re-confirming
  `yarn-mappings-patch-neoforge` actually exists for 1.21.1 early, before writing code).

Known open items, not blockers, revisit later:
- `./gradlew :1.20.1-forge:runClient` (Loom dev-launch) still doesn't work - see the
  Stage 2 note above. Not blocking since a real Forge install is a proven alternative,
  but worth fixing eventually for developer convenience/CI.
- Config-GUI gap on Forge/NeoForge (no Mod Menu equivalent) — still just the acceptable
  v1 gap the plan always anticipated, not newly discovered.
- CI workflows (`.github/workflows/*`) still reference the pre-migration
  `mod_version` property and `runDatagenClient` task name — untouched so far, this is
  Stage 6's job, expected to stay red/irrelevant until then.

Goal: convert this repo from a single Fabric/1.20.1 project into one Gradle workspace
that builds Fabric/Forge/NeoForge across 1.20.1, 1.21.1, 1.21.11, and 26.2, using
[Stonecraft](https://stonecraft.meza.gg) (which wires [Stonecutter](https://stonecutter.kikugie.dev/)
for multi-version and [Architectury Loom](https://docs.architectury.dev/) for multi-loader).

This plan was researched directly against Stonecraft's source
(`gg.meza.stonecraft` on GitHub: `meza/Stonecraft`, `meza/Stonecraft-template`) as of
2026-08, not just its marketing docs — file/property names below are taken from real
`Loom.kt`, `Dependencies.kt`, `ModData.kt`, and the e2e test fixture's
`versions/dependencies/*.properties`. Still, pin exact dependency versions (Loom,
Fabric API, NeoForge, Forge, Cloth Config per-loader artifacts) at implementation time —
this ecosystem moves fast and 26.2 in particular is new enough that build availability
needs a live check, not a memorized number.

## How Stonecraft actually structures a project (read this before Stage 1)

This is **not** the classic Architectury `common` + `fabric` + `forge` + `neoforge`
subproject split. There is one `src/main/java` + `src/main/resources` tree, same as
today. Stonecutter "chisels" (copies) that tree into one Gradle subproject per
`{minecraftVersion}-{loader}` pair (e.g. `1.20.1-fabric`, `1.20.1-forge`,
`26.2-neoforge`) and applies comment-based preprocessor directives to adapt code per
target:

```java
/*? if fabric {*/
import net.fabricmc.api.ModInitializer;
/*?}*/
/*? if forgeLike {*/
/*import net.neoforged.fml.common.Mod;*/
/*?}*/
```

Version/loader differences that aren't structural (a renamed vanilla class, for
example) can also be handled with `stonecutter.replacements` string swaps in
`build.gradle.kts`, scoped to a version predicate.

Key config files this introduces at the repo root:

- `settings.gradle.kts` — declares the version×loader matrix (replaces `vers()`/
  `version()` calls per Minecraft version) and applies `gg.meza.stonecraft` +
  `dev.kikugie.stonecutter`.
- `stonecutter.gradle.kts` — declares which single `{version}-{loader}` target is
  "active" for local IDE/dev work (`./gradlew runClient` etc. without a subproject
  qualifier acts on this one).
- `build.gradle.kts` — the **central script**, shared across every chiseled target.
  Per-loader branching here uses `project.mod.isFabric` / `.isForge` / `.isNeoforge` /
  `.isForgeLike` (a Kotlin extension Stonecraft provides), not Stonecutter comments.
- `versions/dependencies/<mcVersion>.properties` — one file per Minecraft version
  (loader-agnostic), holding `minecraft_version`, `loader_version`, `fabric_version`,
  `forge_version`, `neoforge_version`, and — critically — an optional `yarn_mappings`
  key (see mappings section below).

Gradle/JDK bump: Stonecraft's current stable line (1.10.x, paired with Stonecutter
0.9.x) targets Gradle 9 / JDK 21 toolchains, same territory AGENTS.md already flagged
Loom 1.12+ needing. This migration is a good time to make that jump since it's
happening anyway.

## The mappings decision (why 26.2 is different from the other three)

Stonecraft defaults every version to Mojang's official mappings ("Mojmap") unless that
version's `versions/dependencies/<version>.properties` file sets `yarn_mappings`, in
which case Loom uses Yarn instead (with an Architectury patch layered in for NeoForge
so the same Yarn-named source still resolves there). This repo's existing code is
100% Yarn-named (`net.minecraft.block.FlowerBlock`, etc.), so:

- **1.20.1, 1.21.1, 1.21.11**: set `yarn_mappings` (+ `yarn_mappings_neoforge_patch` for
  the NeoForge target) in each version's properties file. The existing Yarn-named
  source keeps compiling basically as-is across all loaders for these three versions.
  1.21.11 is the last Minecraft release Yarn ever mapped (per AGENTS.md's own note on
  why `gradle.properties` is pinned there) — it still works, just don't expect a newer
  Yarn build to ever exist.
- **26.2**: Minecraft ships fully deobfuscated starting at 26.1 (Mojang's
  post-obfuscation change) — there is no Yarn mapping to apply at all, `yarn_mappings`
  isn't an option, and the source compiled for this target must use Mojang's actual
  class/package names (e.g. `ResourceLocation`, not Yarn's `Identifier`; different
  package paths for several vanilla classes).

Net effect: the 26.2 target is the one place in this whole migration that needs real
source-level adaptation for naming, isolated into its own stage (Stage 5) rather than
spread across the whole migration.

## Platform matrix (per your decision on Forge/1.21+)

Stonecraft's own Loom integration logs a warning that Forge on 1.21+ isn't well
supported by Architectury Loom anymore and recommends NeoForge instead. Per your
choice, Forge is scoped to 1.20.1 only:

| Minecraft version | Fabric | Forge | NeoForge | Mappings |
|---|---|---|---|---|
| 1.20.1  | ✅ | ✅ | — | Yarn |
| 1.21.1  | ✅ | — | ✅ | Yarn |
| 1.21.11 | ✅ | — | ✅ | Yarn (final Yarn build) |
| 26.2    | ✅ | — | ✅ | Mojmap (forced) |

8 build targets total (revised down from an originally-planned 9 — see the
1.20.1-neoforge note below). If Forge on 1.20.1 turns out to have its own Architectury
friction once you're in it, dropping it there too is a one-line change to the
`settings.gradle.kts` matrix — nothing else depends on it existing.

**1.20.1-neoforge dropped (confirmed infeasible, not just deprioritized):** empirically
verified during Stage 2 — `dev.architectury:yarn-mappings-patch-neoforge` (the patch
Stonecraft requires for any Yarn-mapped NeoForge target) was never published for
1.20.1; the earliest available build is 1.20.5+. `net.neoforged:neoforge` itself also
has no 1.20.1 releases (NeoForge's current Maven coordinates/versioning start at
1.20.2; 1.20.1 predates the "neoforge" artifact rename). NeoForge's first appearance in
this matrix is therefore 1.21.1 (Stage 3), not 1.20.1 — this mirrors the existing
"Forge stops at 1.20.1" carve-out, just from the other direction.

## Known scope gap: config GUI on Forge/NeoForge

Mod Menu (which currently hosts the Cloth Config screen via `ModMenuIntegration`) is
Fabric-only — there's no Forge/NeoForge equivalent bundled by default. Cloth Config
itself does publish `cloth-config-forge`/`cloth-config-neoforge` artifacts and may
register its own config-screen factory on those loaders without Mod Menu (verify this
empirically in Stage 2) — but if it doesn't, Forge/NeoForge players get the config file
only, no in-game GUI, for this migration. Treat that as an acceptable v1 gap, not a
blocker; revisit as a follow-up if it matters.

---

## Stage 0 — Tooling bump (still single Fabric/1.20.1, still buildable)

Isolate "new JDK/Gradle" risk from "new build architecture" risk by doing it first,
against the current unmodified project.

1. Bump to JDK 21 and whatever Gradle 9.x line current Loom/Stonecraft needs (check the
   current pinned versions at `README.md`/`gradle.properties` today — Loom 1.17-SNAPSHOT
   already needs JDK 21 to run per the existing comment there).
2. Confirm `./gradlew build` and `./gradlew runClient` still pass with the new
   toolchain, no other changes.
3. Spot-check that Modrinth/CurseForge publishing (if wired in CI) still works — this is
   also a good moment to note current CI workflow files for later (Stage 6 rewrites
   them).

**Verify:** `./gradlew build` green, `runClient` launches, on JDK 21 / new Gradle, with
zero functional changes to the mod.

## Stage 1 — Adopt Stonecraft/Stonecutter scaffolding, single target (1.20.1-fabric)

Bring in the whole toolchain but point it at exactly one target, so this stage's only
variable is "does the new build backbone work," not "does multi-loader work."

1. Replace `settings.gradle` → `settings.gradle.kts`: add `pluginManagement` repos
   (Fabric, Architectury, Forge, NeoForge, kikugie maven), apply `gg.meza.stonecraft` +
   `dev.kikugie.stonecutter`, declare only `"1.20.1-fabric"` in the `stonecutter { shared
   { ... } }` block.
2. Add `stonecutter.gradle.kts` with `stonecutter active "1.20.1-fabric"`.
3. Add `versions/dependencies/1.20.1.properties`: `minecraft_version`, `loader_version`,
   `fabric_version`, `yarn_mappings` (today's pinned values, moved out of
   `gradle.properties`).
4. Replace `build.gradle` → `build.gradle.kts`, moving today's Cloth Config / Mod Menu
   dependency declarations into it, gated on `project.mod.isFabric` even though it's the
   only loader that exists yet (saves a rewrite in Stage 2).
5. Reconcile the existing custom `datagenClient` run config and
   `sourceSets.main.resources.srcDirs += 'src/main/generated'` block with Stonecraft's
   built-in datagen wiring (`configureDatagen` in Stonecraft handles Fabric datagen via
   `FabricApiExtension` automatically) — don't end up with two competing datagen setups.
6. Trim `gradle.properties` down to what Stonecraft expects (`mod.id`, `mod.name`,
   `mod.version`, `mod.group`, `mod.description`, `org.gradle.*`); version-specific keys
   moved to `versions/dependencies/1.20.1.properties` in step 3.

**Verify:** `./gradlew build` and `./gradlew runClient` produce the same mod, same
behavior, same 1.20.1 Fabric jar as before — this stage should be functionally
invisible to a player.

## Stage 2 — Add Forge for 1.20.1 (multi-loader, one Minecraft version)

This is where loader-conditional source splitting actually starts. NeoForge does not
appear in this stage — see the matrix note above; it's added in Stage 3 instead.

**Correction to this stage's original scope:** the initial version of this plan assumed
only the entrypoint (`EmeraldIsleFlora`), the client annotation
(`EmeraldIsleFloraClient`), and the config-GUI gap would need loader-specific handling,
with the rest of the codebase being "common Vanilla API... needing zero changes." That
undersold it. Checked against the actual source, these also use Fabric-API-only classes
with no Forge equivalent: `ModBlocks` (`FabricBlockSettings`, `FabricItemSettings`,
`CompostingChanceRegistry`), `ModItemGroups` (`FabricItemGroup`, `ItemGroupEvents`), and
`ModBoneMealInteraction` (`UseBlockCallback` — this is a Fabric API event, not vanilla,
contrary to what this plan originally claimed). Only `ModDispenserBehavior` and
`GrowableFlower` turned out to be genuinely loader-agnostic vanilla code as originally
assumed. Per-loader hand-written code (via Stonecutter comments), not a shared
Architectury API dependency, was the chosen approach — see the commit history for the
per-class breakdown.

1. Add `forge_version` to `versions/dependencies/1.20.1.properties`; add
   `"1.20.1-forge"` to the `settings.gradle.kts` matrix.
2. Add `mods.toml` next to `fabric.mod.json` in `src/main/resources/META-INF/`, using
   Stonecraft's `${id}`/`${version}`/etc. variable-substitution tokens (it hooks
   `processResources` automatically for `json`/`toml`/`mcmeta` files).
3. Split the mod entrypoint: `EmeraldIsleFlora` needs a Forge `@Mod`-annotated
   no-arg-constructor path (via `FMLJavaModLoadingContext.get().getModEventBus()`, not
   constructor-injected — verify this against Forge's actual 1.20.1 API, not just
   Stonecraft's docs example, which shows a newer/different constructor shape) alongside
   its existing Fabric `ModInitializer.onInitialize()`, via `/*? if fabric {*/`/
   `/*? if forge {*/` blocks.
4. `EmeraldIsleFloraClient`'s `@Environment(EnvType.CLIENT)` annotation is Fabric-only —
   gate client-only registration per-loader instead.
5. Block/item registration (`ModBlocks`) needs a Forge `DeferredRegister<Block>`/
   `DeferredRegister<Item>` + `RegistryObject` path, registered on the mod event bus —
   Forge does not allow direct `Registry.register()` calls at mod-construction time the
   way Fabric does. `FabricBlockSettings`/`FabricItemSettings` can be replaced with
   plain vanilla `AbstractBlock.Settings`/`Item.Settings` on **both** loaders (no
   conditional needed) since no Fabric-only setter is actually used.
   `CompostingChanceRegistry` likewise has a direct vanilla equivalent
   (`ComposterBlock.registerCompostable`) usable on both loaders unconditionally.
6. Creative tab (`ModItemGroups`) needs a Forge `DeferredRegister<CreativeModeTab>` +
   `BuildCreativeModeTabContentsEvent` (for adding into vanilla's existing Natural
   Blocks tab) path.
7. Right-click interaction (`ModBoneMealInteraction`) needs a Forge
   `PlayerInteractEvent.RightClickBlock` path in place of Fabric's `UseBlockCallback`.
8. Cross-shaped block rendering: Fabric keeps its existing `BlockRenderLayerMap` calls
   in `EmeraldIsleFloraClient`; Forge needs no Java code at all here — add
   `"render_type": "minecraft:cutout"` to the affected block model JSON files instead
   (the modern, non-deprecated approach for 1.19+; the same JSON file is shared by every
   loader target, and Fabric ignores the extra key harmlessly).
9. `ModMenuIntegration` stays Fabric-only (see the config-GUI gap noted above); confirm
   whether Cloth Config surfaces its own screen on Forge without it.
10. Verify the bone-meal *mechanic itself* (`ModCommonLogic`, `ModDispenserBehavior`) —
    `DispenserBlock.registerBehavior` and the vanilla block/item classes involved are
    genuinely common vanilla API across loaders; only the *entry point* into that logic
    (step 7 above) needs per-loader code.

**Verify:** `./gradlew build` green for `1.20.1-fabric` and `1.20.1-forge`; launch each
loader's dev client and confirm Bells of Ireland / Bog Rosemary / Bulbous Buttercup
register, render, and bone-meal/harvest correctly.

**Actual outcome:** `1.20.1-fabric` verified fully in-game (registration, rendering,
bone-meal, no regressions from the shared-code changes). `1.20.1-forge` verified at the
build/jar level only — `./gradlew build` is green and jar contents were inspected
directly (mods.toml substitution, cutout render_type in the model JSON, biome_modifier
data, LICENSE, correct exclusion of fabric.mod.json) — but `./gradlew
:1.20.1-forge:runClient` currently crashes in Forge's own bootstrap
(`IllegalStateException: Failed to find system mod: minecraft`, thrown before this
mod's code runs at all) on both the 47.4.10 and 47.3.10 Forge builds. This matches a
known class of Architectury Loom / Forge 1.20.1 dev-launch bug reported by other
projects, not something introduced by this migration. Revisit before a real Forge
release, or if a newer Architectury Loom fixes it.

## Stage 3 — Add 1.21.1 (Fabric + NeoForge)

First new Minecraft version — this is where real vanilla-API-delta handling begins
(registry API, block-state, and world-gen changes between 1.20.1 and 1.21.1 are
non-trivial; check each registry class and the world-gen config for Bells of Ireland
specifically, since that's the one with a world-gen option per AGENTS.md).

1. Add `versions/dependencies/1.21.1.properties` (`yarn_mappings`, `fabric_version`,
   `loader_version`, `neoforge_version`, `yarn_mappings_neoforge_patch`).
2. Add `"1.21.1-fabric"` / `"1.21.1-neoforge"` to `settings.gradle.kts`.
3. Fix compile breaks with version-scoped Stonecutter conditionals
   (`/*? if <1.21.1 {*/ ... /*?}*/`), not by dropping Yarn — both versions stay
   Yarn-mapped here, so most breakage will be genuine vanilla API changes, not mapping
   drift.

**Verify:** all of Stage 2's targets stay green, plus `1.21.1-fabric` and
`1.21.1-neoforge` build and run in-game (registration, rendering, bone-meal/harvest,
world-gen).

## Stage 4 — Add 1.21.11 (Fabric + NeoForge)

Same shape as Stage 3, smaller expected delta (same 1.21.x line). This is also the
last version where Yarn mappings exist at all, so it's worth explicitly confirming the
final Yarn build number resolves before relying on it.

1. Add `versions/dependencies/1.21.11.properties` with the final Yarn build.
2. Add `"1.21.11-fabric"` / `"1.21.11-neoforge"` to the matrix.
3. Handle any remaining API deltas the same way as Stage 3.

**Verify:** full matrix through 1.21.11 (6 targets) green, in-game smoke test on the
two new targets.

## Stage 5 — Add 26.2 (Fabric + NeoForge), the Mojmap-only version

The isolated "hard" stage: no `yarn_mappings` entry exists for this version, so Loom
falls back to Mojmap (raw deobfuscated names) automatically. Expect this to be the
biggest single chunk of source work in the migration.

1. Add `versions/dependencies/26.2.properties` — **no** `yarn_mappings` key —
   `minecraft_version`, `loader_version`, `fabric_version`, `neoforge_version` only.
2. Add `"26.2-fabric"` / `"26.2-neoforge"` to the matrix.
3. Audit every vanilla class/method reference the mod touches for Yarn-name vs.
   Mojang-name divergence (known example: Yarn's `Identifier` vs. Mojang's
   `ResourceLocation`; several vanilla packages are reshuffled too, e.g. block/item
   classes live under different `net.minecraft.world.*` paths in Mojmap than in Yarn).
   Given this codebase is small (~10 classes per AGENTS.md's package inventory),
   per-reference Stonecutter conditionals are likely more tractable here than a
   project-wide `stonecutter.replacements` string-swap rule set — but reach for the
   latter if the same rename recurs across many files.
4. Re-check `BlockRenderLayerMap` and any other Fabric-API-specific call sites for a
   26.2-era Fabric API signature change (verify current Fabric API's actual API surface
   for 26.2 rather than assuming parity with 1.21.11).

**Verify:** full 8-target matrix builds green; in-game smoke test on `26.2-fabric` and
`26.2-neoforge` specifically, since this is the target most likely to have a subtle
naming bug that compiles fine but resolves the wrong vanilla symbol.

## Stage 6 — CI, publishing, and docs

1. Rewrite `.github/workflows/build.yml` (and any release workflow) to build/test the
   full matrix — Stonecutter 0.9/Stonecraft 1.10 fan out native Gradle tasks
   (`build`, `publishMods`) across every chiseled subproject automatically, so this is
   mostly "invoke the root task," not "loop over 9 targets manually."
2. Wire `publishMods { modrinth { ... } curseforge { ... } }` per Stonecraft's DSL,
   preserving today's Modrinth/CurseForge release behavior across all loaders/versions
   from one invocation.
3. Rewrite `AGENTS.md`: the entire "single unsplit source set, client-only-by-convention"
   section is now obsolete (superseded by Stonecutter's chiseled-subproject model +
   loader-conditional comments) and needs replacing, not patching. `CONTRIBUTING.md` and
   `README.md`'s build/requirements sections need the same treatment (JDK 21, new
   `./gradlew` invocations, version×loader matrix).

**Verify:** CI green on a real PR against the new structure; a tagged release actually
produces jars for all 8 targets.

## Stage 7 (optional, out of scope for v1) — Forge/NeoForge config GUI parity

Only pick this up if the config-GUI gap from Stage 2 turns out to matter in practice.
Would mean either confirming Cloth Config's own screen factory covers Forge/NeoForge
without Mod Menu, or building a loader-native config screen for those platforms.

---

## Suggested delivery rhythm

Land each stage as its own commit or PR on `feat/multi-version` (already the checked-out
branch) rather than one giant change — every stage above ends on a green, in-game-tested
build, which is exactly the kind of checkpoint that makes bisecting a regression
tractable later. Re-verify the *previous* stages' targets still build at the end of
each new stage, not just the newly-added ones — Stonecutter's shared central
`build.gradle.kts` means a change made for version N can silently break version N-1.
