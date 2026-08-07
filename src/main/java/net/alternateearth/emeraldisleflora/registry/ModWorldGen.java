package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
/*? if fabric && <26.2 {*/
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
/*?}*/
/*? if fabric && >=26.2 {*/
/*import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
*/
/*?}*/

public final class ModWorldGen {

    /**
     * Fabric-only body: Forge has no equivalent Java API for this - it adds features to
     * biomes via data-driven JSON instead (see
     * data/emeraldisleflora/forge/biome_modifier/*.json), which needs no registration
     * call at all. Still called uniformly from both loaders' entrypoints; it's just a
     * no-op on Forge.
     */
    public static void register() {
        /*? if fabric {*/
        EmeraldIsleFlora.LOGGER.info("Registering World Generation for " + EmeraldIsleFlora.MOD_ID);

        /*? if <26.2 {*/
        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.PLAINS),
            GenerationStep.Feature.VEGETAL_DECORATION,
            RegistryKey.of(
                RegistryKeys.PLACED_FEATURE,
                Identifier.of(EmeraldIsleFlora.MOD_ID, "patch_bells_of_ireland_plains"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.MEADOW),
            GenerationStep.Feature.VEGETAL_DECORATION,
            RegistryKey.of(
                RegistryKeys.PLACED_FEATURE,
                Identifier.of(EmeraldIsleFlora.MOD_ID, "patch_bells_of_ireland_meadow"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.SWAMP),
            GenerationStep.Feature.VEGETAL_DECORATION,
            RegistryKey.of(
                RegistryKeys.PLACED_FEATURE,
                Identifier.of(EmeraldIsleFlora.MOD_ID, "patch_bog_rosemary_swamp"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP),
            GenerationStep.Feature.VEGETAL_DECORATION,
            RegistryKey.of(
                RegistryKeys.PLACED_FEATURE,
                Identifier.of(EmeraldIsleFlora.MOD_ID, "patch_bog_rosemary_mangrove_swamp"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.PLAINS),
            GenerationStep.Feature.VEGETAL_DECORATION,
            RegistryKey.of(
                RegistryKeys.PLACED_FEATURE,
                Identifier.of(EmeraldIsleFlora.MOD_ID, "patch_bulbous_buttercup_plains"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.SUNFLOWER_PLAINS),
            GenerationStep.Feature.VEGETAL_DECORATION,
            RegistryKey.of(
                RegistryKeys.PLACED_FEATURE,
                Identifier.of(EmeraldIsleFlora.MOD_ID, "patch_bulbous_buttercup_sunflower_plains"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.MEADOW),
            GenerationStep.Feature.VEGETAL_DECORATION,
            RegistryKey.of(
                RegistryKeys.PLACED_FEATURE,
                Identifier.of(EmeraldIsleFlora.MOD_ID, "patch_bulbous_buttercup_meadow"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.FOREST),
            GenerationStep.Feature.VEGETAL_DECORATION,
            RegistryKey.of(
                RegistryKeys.PLACED_FEATURE,
                Identifier.of(EmeraldIsleFlora.MOD_ID, "patch_bluebell_forest"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST),
            GenerationStep.Feature.VEGETAL_DECORATION,
            RegistryKey.of(
                RegistryKeys.PLACED_FEATURE,
                Identifier.of(EmeraldIsleFlora.MOD_ID, "patch_bluebell_flower_forest"))
        );
        /*?} else {*/
        /*
        // 26.2: same shape, Mojmap names - BiomeKeys -> Biomes, RegistryKeys -> Registries
        // (key constants; see ModBlocks for the full swap explanation), GenerationStep.
        // Feature -> GenerationStep.Decoration (enum itself renamed too, not just its
        // containing class untouched) - all confirmed via javap against the real Fabric
        // API 26.2 jar's fabric-biome-api-v1 submodule and the real 26.2 client jar.
        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(Biomes.PLAINS),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(
                Registries.PLACED_FEATURE,
                Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "patch_bells_of_ireland_plains"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(Biomes.MEADOW),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(
                Registries.PLACED_FEATURE,
                Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "patch_bells_of_ireland_meadow"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(Biomes.SWAMP),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(
                Registries.PLACED_FEATURE,
                Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "patch_bog_rosemary_swamp"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(Biomes.MANGROVE_SWAMP),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(
                Registries.PLACED_FEATURE,
                Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "patch_bog_rosemary_mangrove_swamp"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(Biomes.PLAINS),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(
                Registries.PLACED_FEATURE,
                Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "patch_bulbous_buttercup_plains"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(Biomes.SUNFLOWER_PLAINS),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(
                Registries.PLACED_FEATURE,
                Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "patch_bulbous_buttercup_sunflower_plains"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(Biomes.MEADOW),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(
                Registries.PLACED_FEATURE,
                Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "patch_bulbous_buttercup_meadow"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(Biomes.FOREST),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(
                Registries.PLACED_FEATURE,
                Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "patch_bluebell_forest"))
        );

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(Biomes.FLOWER_FOREST),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(
                Registries.PLACED_FEATURE,
                Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, "patch_bluebell_flower_forest"))
        );
        */
        /*?}*/

        EmeraldIsleFlora.LOGGER.info("Finished registering World Generation for " + EmeraldIsleFlora.MOD_ID);
        /*?}*/
    }
}
