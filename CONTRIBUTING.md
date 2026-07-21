# Contributing to Emerald Isle Flora

Thanks for looking at contributing. This doc covers the "how do I actually do things"
side of working on this project. For "why is the code organized this way" and coding
conventions/pitfalls, see [AGENTS.md](AGENTS.md) — this doc will point you there rather
than duplicate it.

## Setting up

See the README's "Requirements" and "Building the mod" sections for JDK/Gradle setup —
not repeated here so the two docs can't drift out of sync with each other.

### Running/debugging from an IDE

If you open this project in **IntelliJ IDEA** (recommended — this is what Fabric's
tooling is best tested against) or VS Code with the Java + Gradle extensions, importing
the project via Gradle will automatically create `Minecraft Client` and `Minecraft
Server` run configurations. Use those to run with breakpoints, hot-swap, etc. Make sure
your IDE's project SDK is JDK 21.

Useful Gradle tasks beyond `runClient` (see README):

```bash
# A dedicated server, in the same git-ignored run/ folder as runClient
./gradlew runServer

# Re-generate readable (deobfuscated) Minecraft source, useful for
# "Go to definition" / "Find usages" on vanilla code in your IDE
./gradlew genSources
```

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

See [AGENTS.md](AGENTS.md) for the full package layout and naming conventions.

## Adding a grow/harvest-style mechanic (a bigger example)

The Bells of Ireland → Grown Bells of Ireland bone-meal mechanic (`util/ModBlocks.java`,
`util/ModCommonLogic.java`, `util/ModBoneMealInteraction.java`,
`util/ModDispenserBehavior.java`) is the reference pattern if you're building something
similar for another flower:

1. A shared logic method (`ModCommonLogic.growOrHarvest`) that both entry points below
   call, rather than duplicating the logic per entry point. If the mechanic should be
   config-gated, gate it **inside this shared method**, not in each caller separately —
   see how `enableGrownFlowerHarvesting` is checked in exactly one place.
2. A `UseBlockCallback` registration (`ModBoneMealInteraction`) for the by-hand path.
3. A `DispenserBlock.registerBehavior(...)` registration (`ModDispenserBehavior`) for
   the dispenser path — and if you're registering a behavior for an item that already
   has vanilla behavior (bone meal does), you **must** reimplement the vanilla fallback
   for cases that aren't yours, or you'll silently break that item's normal dispenser
   behavior for every other block in the game. See the fallback block in
   `ModDispenserBehavior` for the pattern.
4. If the mechanic should have a config toggle, add the field to `ModConfig`, wire it
   into `ModMenuIntegration`, add its translation keys, and gate it per step 1 above —
   all in the same change.

## Before opening a PR

- [ ] `./gradlew build` passes
- [ ] You've actually run `./gradlew runClient` and checked your change in-game — there
      is no automated test suite yet (see AGENTS.md's "Build & verification commands"),
      so this is the actual bar, not optional polish. Rendering bugs, wrong registry
      ordering, and client code leaking into common code are the recurring categories of
      bug that only show up at runtime, not at compile time.
- [ ] New content has translation keys, and (if applicable) a texture/model, loot table,
      and tag entries — see AGENTS.md's "Conventions to follow when adding content".
- [ ] If you copied a recipe/advancement/loot table JSON from elsewhere as a starting
      point, every mod-ID reference inside it actually got updated. This exact mistake
      has happened in this repo before (a stray reference to a different mod's ID in an
      advancement file) — these fail silently at runtime, not at build time.
- [ ] If you touched `README.md`, `AGENTS.md`, or this file and the change makes any of
      the others inaccurate, update those too in the same PR rather than letting docs
      drift — this has also happened in this repo before and taken multiple follow-up
      passes to fully catch up.
