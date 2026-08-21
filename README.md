# Emerald Isle Flora

A Minecraft mod adding flora inspired by Ireland, built with [Stonecraft](https://stonecraft.meza.gg) to target multiple Minecraft versions and mod loaders from a single codebase.

| Minecraft | Loaders          |
|-----------|------------------|
| 1.20.1    | Fabric, Forge    |
| 1.21.1    | Fabric, NeoForge |
| 1.21.11   | Fabric, NeoForge |
| 26.2      | Fabric, NeoForge |

## Current Content

Four decorative flowers, each with the same shape of mechanic:

| Flower            | Stew Effect  | Dye    | Natural Generation Biome(s)      |
|-------------------|--------------|--------|----------------------------------|
| Bells of Ireland  | Regeneration | Green  | Meadow, Plains                   |
| Bluebell          | Jump Boost   | Blue   | Forest, Flower Forest            |
| Bog Rosemary      | Nausea       | Pink   | Swamp, Mangrove Swamp            |
| Bulbous Buttercup | Nausea       | Yellow | Plains, Sunflower Plains, Meadow |


### Flower Variants:

- Standard Flower
- Grown Flower (Crafted from 2 Standard Flowers or Bonemeal Standard Flower)
- Potted Standard Flower
- Potted Grown Flower (Place Grown Flower or Bonemeal Potted Standard Flower)

### Notes
Flowers and their grown variants are tagged with vanilla's `flowers`/`small_flowers` tags (block and item), and potted variants with `flower_pots`.

## Configuration

- `enableGrownFlowerHarvesting` (default: `true`) - toggles just the repeatable-harvest half of the grown flower mechanic; growing a base flower into its grown variant is unaffected either way.
- `enableGrownFlowering` (default: `true`) - toggles just the growing of a base flower into its grown variant; the repeatable-harvest mechanic is not impacted.

Config is saved to `config/emeraldisleflora.json`.

**Mod Menu support** (Fabric, below 26.2 only) — if [Mod Menu](https://modrinth.com/mod/modmenu) is installed, an in-game config screen built with [Cloth Config](https://modrinth.com/mod/cloth-config) is available from the mod list. Without it, the mod works exactly the same — config can still be hand-edited via the JSON file. Not available on Forge/NeoForge or on 26.2.

## Dependencies

- [Fabric API](https://modrinth.com/mod/fabric-api) — required on Fabric targets.
- [Cloth Config](https://modrinth.com/mod/cloth-config) — required on Fabric targets below 26.2.

## Contributing

See [CONTRIBUTING.md](docs/CONTRIBUTING.md)

## Requirements

- **JDK 21** to build and run the Gradle tooling itself. Toolchain-managed per target by Stonecraft/Loom - you don't need multiple JDKs installed, just 21 on `JAVA_HOME` (or configured via `org.gradle.java.home`).
- No local Gradle install needed - this project includes the Gradle Wrapper (`gradlew` / `gradlew.bat`), which downloads the exact Gradle version this project expects (9.6.1) the first time you run it.

## Building the mod

From the project root:

```bash
# macOS / Linux
./gradlew build

# Windows
gradlew.bat build
```

This builds **every target in the matrix above** in one invocation - Stonecutter fans `build` out across all 8 subprojects automatically. To build just one target:

```bash
./gradlew :1.21.11-fabric:build
```

### Note

The first run will download Minecraft, mappings, and dependencies for every target, so it can take a while — subsequent builds are much faster, especially scoped to one target.

## Running the test client

From the project root:

```bash
# macOS / Linux
./gradlew :1.21.11-fabric:runClient

# Windows
gradlew.bat :1.21.11-fabric:runClient
```

## Command List

```bash
# macOS / Linux
./gradlew :1.21.11-fabric:build           # Build Minecraft 1.21.11 for Fabric
./gradlew :1.21.11-fabric:runClient       # Run test client for Minecraft 1.21.11 for Fabric
./gradlew :1.21.11-fabric:deployToPrism   # Deploy mod to 1.21.11-fabric Prism instance
./gradlew :1.21.11-fabric:runDatagen      # Regenerate the Data that comes from code for Minecraft 1.21.11 for Fabric
```

## License

Released under the [MIT License](LICENSE).

## Appreciation 

Mod icon lovingly made by a close friend, thanks.