# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/).
Each version's section below is published verbatim as that version's changelog on
Modrinth (see `publishMods` in `build.gradle.kts`) — write entries with players in
mind, not as a commit log.

## [1.6.5] - 2026-08-08

### Added

- Bees can now pollinate and be attracted to this mod's flowers on 1.21.1, 1.21.11, and
  26.2, matching how they already worked on 1.20.1.

### Fixed

- Dye and grown-flower crafting recipes work again on 1.21.1, 1.21.11, and 26.2.
- All flowers (regular, grown, and potted) drop correctly again when broken on 1.21.1, 1.21.11, and 26.2. 
- Grown flowers also correctly keep themselves when broken with Silk Touch or Shears again, instead of always reverting to 2 of the base flower.
- Fixed flowers silently disappearing from vanilla's `flowers`/`small_flowers`/`flower_pots` tags on 1.21.1, 1.21.11, and 26.2.
- Composting this mod's flowers works now, on every loader and Minecraft version.

## [1.6.0] - 2026-08-07

### Added

- **Bluebell** — a fourth flower, granting Jump Boost in a suspicious stew. Naturally
  generates in Forest and Flower Forest biomes. Has the same grown/potted variants,
  composting, and dye-crafting (blue dye) as the other three flowers.
- Official support for the full Fabric/Forge/NeoForge × 1.20.1/1.21.1/1.21.11/26.2
  build matrix (8 targets total) — this mod now targets far more Minecraft versions
  and loaders than before, built from one shared codebase.

### Fixed

- Forge 1.20.1 couldn't place any of this mod's flowers into flower pots (vanilla
  flowers worked fine). Only NeoForge had received this fix previously.

## [1.5.0] - 2026-08-05

### Added

- **Bulbous Buttercup** — a third flower, granting Nausea in a suspicious stew.
  Naturally generates in Plains, Sunflower Plains, and Meadow biomes. Has the same
  grown/potted variants, bone meal growing/harvesting, and composting as the other
  flowers, and is craftable into yellow dye.

### Notes

- Published manually to Modrinth for every supported loader and Minecraft version,
  ahead of the automated multi-target publish pipeline introduced in 1.6.0.

## [1.4.0] - 2026-07-29

### Added

- **Bog Rosemary** — a second flower, granting Nausea in a suspicious stew. Naturally
  generates in Swamp and Mangrove Swamp biomes. Has the same grown/potted variants,
  bone meal growing/harvesting, and composting as Bells of Ireland, and is craftable
  into pink dye.

## [1.2.0] - 2026-07-27

### Added

- Bells of Ireland now naturally generates in the world, in Plains and Meadow biomes.
- Two new config options (also editable via Mod Menu, if installed): "Enable Flowers
  Growing" and "Enable Grown Flower Harvesting", letting you turn off either half of
  the bone-meal growing/harvesting mechanic independently.

## [1.0.0] - 2026-07-20

### Added

- Bone meal now grows a Bells of Ireland (or its potted variant) into a fully "grown"
  flower over several visual stages. Bone-mealing an already-grown flower drops an
  extra flower item without destroying the block, making it a renewable source.

### Fixed

- The advancement recipe unlock for crafting green dye from Bells of Ireland pointed
  at the wrong item ID and never actually triggered.

## [0.5.0] - 2026-07-15

### Added

- Initial release. **Bells of Ireland** — a flower (and potted variant) granting
  Regeneration in a suspicious stew, craftable into green dye.
