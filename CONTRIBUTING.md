# Contributing to Emerald Isle Flora

Thanks for looking at contributing. This doc covers the "how do I actually do things"
side of working on this project. For "why is the code organized this way" and coding
conventions/pitfalls, see [AGENTS.md](AGENTS.md) — this doc will point you there rather
than duplicate it. **Read AGENTS.md's "How this repo is actually structured" section
first** if this is your first time in this codebase — it's a Stonecutter-chiseled,
multi-version/multi-loader project from one source tree, not a normal single-target mod,
and that changes how almost everything below works.

## Setting up

See the README's "Requirements" and "Building the mod" sections for JDK/Gradle setup —
not repeated here so the two docs can't drift out of sync with each other.

### Running/debugging from an IDE

If you open this project in **IntelliJ IDEA** (recommended — this is what Fabric's
tooling is best tested against) or VS Code with the Java + Gradle extensions, importing
the project via Gradle will create one `Minecraft Client`/`Minecraft Server` run
configuration pair **per target** (e.g. `1.21.11-fabric runClient`), not just one. Make
sure your IDE's project SDK is JDK 21.

Your IDE's code-completion/navigation for `src/main/java` follows whichever version is
currently **active** — set via `stonecutter active "<target>"` in
`stonecutter.gradle.kts` (the line is marked `DO NOT EDIT` — change it through the
Stonecutter Gradle tasks instead: `./gradlew "Set active project to <target>"`, or your
IDE's Stonecutter plugin if it has one). This only affects what your editor resolves
symbols against for editing convenience; it has no effect on what actually gets built —
every `./gradlew build`/`:<target>:build` invocation chisels and compiles fresh
regardless of which project is "active."

Useful Gradle tasks beyond `:<target>:runClient` (see README):

```bash
# A dedicated server, in the same git-ignored run/ folder as runClient, for one target
./gradlew :<target>:runServer

# Re-generate readable (deobfuscated) Minecraft source for one target, useful for
# "Go to definition" / "Find usages" on vanilla code in your IDE
./gradlew :<target>:genSources
```

## Adding your first block or item

1. Register it in `registry/ModBlocks.java` (or a new registry class, e.g.
   `ModItems.java`, if you're adding an item that isn't a `BlockItem`), following the
   pattern already there — including its Stonecutter version/loader conditionals if the
   registration API differs across the matrix (see AGENTS.md's Stonecutter section; most
   new content follows an existing flower's shape closely enough that you can copy its
   conditionals verbatim and just swap identifiers).
2. Call your new registration method from every loader's entrypoint in
   `EmeraldIsleFlora` (Fabric's `onInitialize`, Forge/NeoForge's `onRegister` event
   handlers).
3. Add it to the creative tab inside `registry/ModItemGroups.java`'s existing
   entries-callback.
4. Add a translation key to
   `src/main/resources/assets/emeraldisleflora/lang/en_us.json`, and any textures/models
   under `src/main/resources/assets/emeraldisleflora/`.
5. If it's a cross-shaped block (a flower, sapling, etc.), see AGENTS.md's "Cross-shaped
   blocks" convention — the render-layer registration needed (or not) differs by
   loader *and* Minecraft version.
6. If it should be craftable, compostable, or advancement-tracked like the existing
   flowers, add the matching files under `src/main/resources/data/emeraldisleflora/`
   (recipes, advancements) and `src/main/resources/data/minecraft/tags/` (vanilla tag
   hookups).
7. If it should naturally generate, see "Adding/changing natural worldgen" below —
   **don't forget to regenerate the checked-in datagen JSON**, or the block simply won't
   spawn in the world despite the Java code looking correct.

See [AGENTS.md](AGENTS.md) for the full package layout and naming conventions.

## Adding a grow/harvest-style mechanic (a bigger example)

The bone-meal grow/harvest mechanic (`registry/ModBlocks.java`, `registry/GrowableFlower.java`,
`util/ModCommonLogic.java`, `util/ModBoneMealInteraction.java`,
`util/ModDispenserBehavior.java`) is the reference pattern if you're building something
similar for another flower — the three existing flowers already share this exact
mechanic, so a fourth flower needs no new plumbing here, just new entries following the
same pattern:

1. A shared logic method (`ModCommonLogic.growOrHarvest`) that both entry points below
   call, rather than duplicating the logic per entry point. If the mechanic should be
   config-gated, gate it **inside this shared method**, not in each caller separately —
   see how `enableGrownFlowerHarvesting`/`enableGrownFlowering` are each checked in
   exactly one place.
2. A by-hand interaction entry point (`ModBoneMealInteraction`) — a Fabric
   `UseBlockCallback` on Fabric, a `PlayerInteractEvent.RightClickBlock` listener on
   Forge/NeoForge (shared verbatim across Forge/NeoForge via `forgeLike`).
3. A `DispenserBlock.registerBehavior(...)` registration (`ModDispenserBehavior`) for
   the dispenser path — and if you're registering a behavior for an item that already
   has vanilla behavior (bone meal does), you **must** reimplement the vanilla fallback
   for cases that aren't yours, or you'll silently break that item's normal dispenser
   behavior for every other block in the game. See the fallback block in
   `ModDispenserBehavior` for the pattern.
4. If the mechanic should have a config toggle, add the field to `ModConfig`, wire it
   into `ModMenuIntegration`, add its translation keys, and gate it per step 1 above —
   all in the same change.

## Adding/changing natural worldgen

1. Add (or edit) the configured feature in `registry/ModConfiguredFeatures.java` and the
   placed feature in `registry/ModPlacedFeatures.java`.
2. Wire the biome injection **twice** — once in `registry/ModWorldGen.java` (Fabric,
   `BiomeModifications.addFeature(...)`) and once as declarative JSON under
   `data/emeraldisleflora/{forge,neoforge}/biome_modifier/` (Forge/NeoForge don't use a
   Java API for this at all).
3. **Regenerate the checked-in datagen JSON** — the bootstrap methods in step 1 are only
   consumed at *datagen* time; every target reads the static, checked-in JSON at
   *runtime*, not the Java bootstrap directly. Forgetting this step is a silent,
   no-compile-error bug: the code looks right, `./gradlew build` stays green, and the
   feature just never spawns.

   There are two checked-in trees, one per mapping scheme (see AGENTS.md's Yarn-vs-Mojmap
   section) — regenerate **both**, from **one Fabric target on each side**, as two
   separate commands:

   ```bash
   ./gradlew :1.20.1-fabric:runDatagen   # -> src/main/generated/yarn (any Yarn target works)
   ./gradlew :26.2-fabric:runDatagen     # -> src/main/generated/mojmap
   ```

   **Don't** run `chiseledDatagen` (every target at once) or two same-side Fabric
   targets' `runDatagen` together for this — they write to the same shared output
   directory and race on Fabric's own stale-file-cleanup cache, which has actually
   deleted correct, already-committed files when this was tried. One command per side,
   run sequentially, is both sufficient (the JSON only depends on which side of the
   Yarn/Mojmap split ran, not the exact game version within a side) and safe.
4. `git diff -- src/main/generated` after regenerating — if it's non-empty, commit the
   diff alongside your Java changes. CI checks this (see
   `.github/actions/build-mod/action.yml`'s `verify-datagen` step) and will fail the
   build if they've drifted apart.

## Adding/changing recipes (dye-from-flower, grown-from-flowers)

Recipe/advancement JSON is datagen'd too (`data/ModRecipeProvider.java`), not
hand-written — Mojang's own recipe data format changed twice across this mod's version
range (the directory was renamed `recipes`/`advancements` -> `recipe`/`advancement`, and
the recipe `result` field was renamed `item` -> `id`, both at 1.21; the `ingredients`
format was further flattened from `{"item": "x"}` objects to bare `"x"` strings at
1.21.11), confirmed by decompiling the real game's `ItemStack`/`Ingredient` codecs and
`RegistryKeys.RECIPE`'s registry path for each version, not assumed from changelog notes.
Hand JSON shared unconditionally across every Stonecutter target silently broke wherever
the format had moved on (see issue #17).

All four Minecraft versions are covered, split into four full sibling Stonecutter
branches in `ModRecipeProvider.java` (deliberately not nested — a `<1.21`/`else` split
nested inside a `<1.21.11` block corrupted Stonecutter's output the moment a target
outside `<1.21.11` needed the whole block disabled, a new failure mode; see its own doc
comment for the full per-branch breakdown of what changed and why). Add new recipes in
**every** branch, kept in sync the same way `EmeraldIsleFlora.java`'s loader-specific
constructors are.

**Regenerate the checked-in datagen JSON.** Unlike worldgen's JSON above, recipe output
can't share `src/main/generated`/`-mojmap` at all, even across a regeneration run that's
otherwise safe: Fabric's own recipe-provider cache identity ("Recipes", from vanilla
`RecipeProvider.getName()`, which is `final` and can't be overridden) is the same
regardless of which format a given Stonecutter branch emits, so a second format group's
regen run into the same directory would see the first group's files as stale and delete
them — confirmed by inspecting the real per-provider cache manifest Fabric writes under
`generated/.cache/`, not assumed. Each recipe-format group therefore gets its own
permanently-separate directory, populated by running datagen into the *shared* directory
as normal and then relocating just the recipe output out into its own directory:

```bash
./gradlew :1.20.1-fabric:runDatagen
rm -rf versions/data/1.20.1/src/main/resources/data/emeraldisleflora/{recipes,advancements}
mkdir -p versions/data/1.20.1/src/main/resources/data/emeraldisleflora
mv src/main/generated/yarn/data/emeraldisleflora/recipes versions/data/1.20.1/src/main/resources/data/emeraldisleflora/recipes
mv src/main/generated/yarn/data/emeraldisleflora/advancements versions/data/1.20.1/src/main/resources/data/emeraldisleflora/advancements

./gradlew :26.2-fabric:runDatagen
rm -rf versions/data/1.21.11-plus/src/main/resources/data/emeraldisleflora/{recipe,advancement}
mkdir -p versions/data/1.21.11-plus/src/main/resources/data/emeraldisleflora
mv src/main/generated/mojmap/data/emeraldisleflora/recipe versions/data/1.21.11-plus/src/main/resources/data/emeraldisleflora/recipe
mv src/main/generated/mojmap/data/emeraldisleflora/advancement versions/data/1.21.11-plus/src/main/resources/data/emeraldisleflora/advancement
```

`src/main/generated/yarn`/`mojmap` should end up containing only
`data/emeraldisleflora/worldgen` afterward - if `recipes`/`recipe`/etc. are still sitting
there, a move step above didn't run (or ran against the wrong directory) and needs to be
redone before committing, or you'll end up committing the same directory's worldgen
output twice pointlessly (harmless, but a sign the step was skipped). `git diff --
src/main/generated versions/data/1.20.1 versions/data/1.21.11-plus` after regenerating —
commit the diff alongside your Java changes.

**1.21.1/1.21.11-fabric's `runDatagen` crashes** on a real, reproduced-on-two-
independent-machines Fabric API bug, not a sandbox artifact - confirmed via the full
crash log: `fabric-mining-level-api-v1`'s `SwordItemMixin` fails to apply against
`net.minecraft.item.SwordItem` for this exact Minecraft/mappings build, specifically in
the dev-launch bootstrap sequence (`Bootstrap.initialize()` → `Blocks.<clinit>`), not
confirmed to affect real installs. No newer Fabric API release exists for 1.21.1 to try
instead (`0.116.15+1.21.1` is already latest); 1.21.11 hits the same class of bug.

For those two, this was worked around by hand-deriving their directories
(`versions/data/1.21.1`, and the `>=1.21.11` half of
`versions/data/1.21.11-plus`) directly from a real, verified run's output
(1.20.1's for 1.21.1; 26.2's for 1.21.11, since their schemas are identical - confirmed,
not assumed) — not by guessing the format, since every change applied was already
independently confirmed against the real target codecs before `ModRecipeProvider` was
written. Don't repeat this by hand again for a future format group without similarly-
solid confirmation of the exact target schema first - `ModRecipeProvider`'s doc comment
has the full per-branch reasoning. Revisit with a real `runDatagen` run for these two if
the Fabric API bug is ever fixed upstream.

## Adding/changing loot tables

Every loot table (`src/main/loot-tables-1.20.1` for 1.20.1,
`src/main/loot-tables-1.21.1-plus` for everything else) is genuinely hand-written JSON,
**not** datagen'd - reaching for datagen here would've meant either duplicating a lot of
loot-table-building code by hand or reaching into a non-public Fabric API class for 26.2
(its loot-table datagen wrapper there doesn't extend vanilla's `BlockLootSubProvider`,
unlike every earlier version), not worth it once both shapes below were independently
confirmed correct against real shipped vanilla loot tables.

**All 16 loot tables need both directories, not just the 4 grown-flower ones with a
Silk Touch/Shears condition** - the directory itself was *also* renamed at 1.21,
`loot_tables` -> `loot_table` (singular; `data/emeraldisleflora/loot_table/blocks/` on
1.21.1+, still `data/emeraldisleflora/loot_tables/blocks/` on 1.20.1), the same pattern
as recipes'/advancements' directory renames - confirmed via `RegistryKeys.LOOT_TABLE`'s
real registry path and by comparing vanilla's own shipped
`data/minecraft/loot_table{,s}/blocks/dandelion.json` across all four versions. This was
missed on the first pass of this fix (only the 4 grown-flower tables' *content* was
checked, not the directory name shared by all 16), which meant literally none of this
mod's loot tables were discovered at all on 1.21+ until caught by real in-game testing -
regular, completely unmodified flowers dropped nothing on 1.21.1/26.2, not just the
grown ones with the Silk Touch condition. **If you ever see "nothing drops" across
every block, not just the ones with a tool-dependent condition, suspect the directory
name first, not the condition JSON** - that's what happened here.

Grown flowers additionally use a real Silk Touch/Shears condition (break with either and
get the grown block back; otherwise get 2 of the base flower) - only this part changed
shape at 1.21, not just directory: `"items": [x]` flattened to `"items": x`, and the
enchantment predicate restructured around a generic `"predicates"` map keyed by
component-predicate-type id, e.g. `"predicates": {"minecraft:enchantments": [...]}`, part
of the same 1.21 data-components overhaul that changed recipes - confirmed identical
across 1.21.1/1.21.11/26.2 by decompiling the real predicate codecs and comparing
vanilla's own shipped loot tables (`azalea_leaves.json`) across all four versions.
Regular/potted flowers just always drop themselves unconditionally (no tool-dependent
condition), so only their directory changes, not their content.

If a future block needs the same Silk Touch/Shears pattern, add it to **both**
directories using the same two shapes - see either directory's existing files for the
exact structure per group. For anything simpler (no tool condition), just make sure it
exists, unchanged, in both directories under the correct folder name for each.

## Adding/changing vanilla tags (data/minecraft/tags)

Same directory-rename pattern again, for the vanilla `flowers`/`small_flowers`/
`flower_pots` block and item tags this mod contributes to
(`data/minecraft/tags/{blocks,items}/*.json`): `tags/blocks`/`tags/items` ->
`tags/block`/`tags/item` (singular) at 1.21, confirmed by comparing vanilla's own real
`data/minecraft/tags/block{,s}/flowers.json` across all four versions. Unlike loot
tables, the file *content* doesn't change shape at all here (still a plain
`{"replace": ..., "values": [...]}`) - only the directory name does, so the exact same
content just needs to exist under both directory names:
`src/main/tags-1.20.1/data/minecraft/tags/{blocks,items}/` and
`src/main/tags-1.21.1-plus/data/minecraft/tags/{block,item}/`. If you add a new tag
entry or a new tag file, add it identically to both directories.

Membership in the plain `flowers`/`small_flowers` tags isn't enough to make a block
bee-attractive on every version - confirmed by decompiling the real `BeeEntity`/`Bee`
class across all four versions, which flower tag it actually checks changed twice, not
just once:
- `1.20.1`: checks `ItemTags.FLOWERS`/`BlockTags.FLOWERS` directly - already covered by
  the existing `flowers` tags, no extra tag needed.
- `1.21.1`: item side switched to the new `ItemTags.BEE_FOOD` (`bee_food`), but the
  block side (pollination target) still checks the old `BlockTags.FLOWERS` - a genuine
  transitional/mixed state, not a clean cutover. Needs `bee_food` (item) added; `flowers`
  (block) already covers it.
- `1.21.11`/`26.2`: block side also switched, to `BlockTags.BEE_ATTRACTIVE`
  (`bee_attractive`) - the fully-migrated state. Needs both `bee_food` (item) and
  `bee_attractive` (block).

`data/minecraft/tags/block/bee_attractive.json` is included in the whole
`tags-1.21.1-plus` directory uniformly (covering 1.21.1 too, even though 1.21.1's own
`Bee` class doesn't consult it) rather than a third, more precisely-scoped directory -
tags are freeform, an unreferenced one is simply inert on that version, not an error,
confirmed vanilla itself doesn't even ship a `bee_attractive` tag file at 1.21.1 (only
`bee_food`) - so this is a deliberate simplification, not an oversight.

## Adding/changing compostable items

Composting is registered in `ModBlocks.registerComposting()` by writing directly into
vanilla's `ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE`/`COMPOSTABLES` static field
(same field, renamed at 26.2 - see the version-rename table already in this file).
Fabric and (old, 1.20.1) Forge both still read straight from this field at composting
time - confirmed by decompiling `ComposterBlock`'s real `useOnBlock`/`insertItem`/
`addItem` methods in both loaders' shipped jars, neither patches it.

NeoForge is different on **every** version this mod targets (1.21.1, 1.21.11, 26.2):
NeoForge patches `ComposterBlock` to deprecate that field and read exclusively from its
own `neoforge:compostables` data map instead - confirmed by decompiling NeoForge's own
`ComposterBlock.java.patch` for all three versions (byte-for-byte identical patch
content across all three), which redirects every `COMPOSTABLES.containsKey`/`getFloat`
call site to a new `getValue(ItemStack)` that reads
`item.getData(NeoForgeDataMaps.COMPOSTABLES)` and ignores the static field entirely.
Writing to the Java field on NeoForge compiles and runs without error, it's just
silently ineffective - the classic "no crash, just doesn't work" shape this project has
run into with every other data-driven registry migration so far.

The real NeoForge-side fix is a data map JSON, merged the same way tags are (any mod can
contribute entries to the same virtual path, keyed by the *data map's own* namespace,
not the contributing mod's): `data/neoforge/data_maps/item/compostables.json`, format
confirmed identical across all three NeoForge versions (`values` object keyed by item
ID, each an object with a `chance` float):
```json
{
  "values": {
    "emeraldisleflora:bells_of_ireland": { "chance": 0.65 }
  }
}
```
Kept in the shared, all-loader `src/main/resources` dir rather than split by version -
it's simply inert, unreferenced data on Fabric/Forge (same freeform-data reasoning as
this project's vanilla tag files), and no version split is needed since the schema
doesn't change across 1.21.1/1.21.11/26.2. If you add a new compostable item, add its
`chance` to **both** `registerComposting()` (for Fabric/Forge) **and** this JSON file
(for NeoForge) - the two are independent, loader-specific sources of truth for what
looks like one feature.

`registerComposting()` also has to pass `.asItem()` on every block, not the block
reference itself - `ITEM_TO_LEVEL_INCREASE_CHANCE`/`COMPOSTABLES` is generically typed
`Object2FloatMap<ItemConvertible>`/`<ItemLike>`, and `.put(...)` is the plain generic map
method, it does not convert for you. Vanilla's own bootstrap always converts explicitly
(confirmed by decompiling `ComposterBlock.registerCompostableItem`, private, which calls
`item.asItem()` before every `.put(...)`). A `Block` and its `Item` are different objects
with default (identity) `equals`/`hashCode`, so a `.put(block, chance)` entry can never
match a real lookup, which always keys off `itemStack.getItem()`, an `Item`.

Fixing that alone was still not enough on Forge 1.20.1, even after it fixed Fabric - a
second bug: `Block.asItem()` lazily caches its result forever after the first call
(confirmed by decompiling the real 1.20.1 jar - it's backed by a private `cachedItem`
field, computed once via `Item.fromBlock(this)`, never recomputed). Fabric's `register()`
is one linear pass, so by the time `registerComposting()` ever runs once, every block
already has its item. Forge/NeoForge's `onRegister(RegisterEvent)` is event-driven
instead: `RegisterEvent` fires once *per registry*, so `onRegister` actually runs
multiple times total, and `event.register(key, ...)` silently no-ops whenever the
current dispatch's registry doesn't match `key`. Calling `registerComposting()`
unconditionally after both `event.register(...)` calls meant it also ran during
whichever *other* registry's dispatch happened to fire first - permanently caching
`Items.AIR` into `.asItem()` before any `BlockItem` existed, before the real, later
`ITEMS` dispatch ever got a chance to fix it. The fix: call `registerComposting()` from
*inside* the `ITEMS`-registry helper lambda itself, in every `onRegister()` variant
(Forge, and all three NeoForge version branches) - guaranteeing it only ever runs once,
already after that same call's own `BlockItem` construction. If you add a new
compostable item, keep the call there, not back at the outer method level.

## Real-install testing (deployToPrism)

Loom's dev-launch (`runClient`) is currently broken or misleading on several targets in
this matrix (see AGENTS.md's "Other gotchas"), and has repeatedly missed or actively
hidden real bugs during this project's version-migration work. For anything touching
registration, rendering, or interaction logic, prefer testing a real installed instance:

```bash
./gradlew :<target>:deployToPrism   # copies that target's jar into a matching
                                     # Prism Launcher instance's mods folder, if one
                                     # exists at the configured path
./gradlew deployToPrism             # same, across every target with a matching instance
```

The task looks for a Prism Launcher instance whose **folder name exactly matches the
chiseled target name** (e.g. `1.21.11-neoforge`) under `prismInstancesDir` (defaults to
a path in `build.gradle.kts`; override via `-PprismInstancesDir=...` or a
`gradle.properties` entry if yours differs). Targets with no matching instance are
skipped, not failed, so this is safe to run across the whole matrix even if you only
have a few instances set up locally. It doesn't launch the instance for you — deploy,
then launch it yourself from Prism.

## Before opening a PR

- [ ] `./gradlew build` passes for the **whole matrix**, not just the target(s) you were
      actively iterating on.
- [ ] You've actually deployed and launched a real instance (`deployToPrism`, see above)
      for at least the target(s) your change most directly affects, and checked it
      in-game — there is no automated test suite yet, and `runClient`/a green build alone
      have both missed real bugs in this repo before (see AGENTS.md). This is the actual
      bar, not optional polish.
- [ ] New content has translation keys, and (if applicable) a texture/model, loot table,
      tag entries, and — if it naturally generates — regenerated datagen JSON on **both**
      sides of the Yarn/Mojmap split (see "Adding/changing natural worldgen" above). See
      AGENTS.md's "Conventions to follow when adding content" for the full list.
- [ ] If you copied a recipe/advancement/loot table JSON from elsewhere as a starting
      point, every mod-ID reference inside it actually got updated. This exact mistake
      has happened in this repo before (a stray reference to a different mod's ID in an
      advancement file) — these fail silently at runtime, not at build time.
- [ ] If a code change is version- or loader-specific, double-check any *existing* bare
      version condition it sits near still means what you think for every target that
      condition now needs to cover — see AGENTS.md's "raw numeric version comparison"
      gotcha before assuming a `>=`/`<` check written for an older matrix still only
      matches what it originally did.
- [ ] If you touched `README.md`, `AGENTS.md`, or this file and the change makes any of
      the others inaccurate, update those too in the same PR rather than letting docs
      drift — this has also happened in this repo before and taken multiple follow-up
      passes to fully catch up.
