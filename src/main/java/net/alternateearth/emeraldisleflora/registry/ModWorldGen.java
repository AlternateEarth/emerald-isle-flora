package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;

/*? if fabric && <26.2 {*/
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
/*?}*/

/*? if fabric && >=26.2 {*/
/*import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
*/
/*?}*/

public final class ModWorldGen {

    /**
     * Fabric-only: Forge adds features via data-driven JSON instead
     * (data/emeraldisleflora/forge/biome_modifier/*.json), so this is a no-op there.
     */
    public static void register() {
        /*? if fabric {*/
        EmeraldIsleFlora.LOGGER.info("Registering World Generation for " + EmeraldIsleFlora.MOD_ID);

        /*? if <26.2 {*/
        addFeature(BiomeKeys.PLAINS, "patch_bells_of_ireland_plains");
        addFeature(BiomeKeys.MEADOW, "patch_bells_of_ireland_meadow");
        addFeature(BiomeKeys.SWAMP, "patch_bog_rosemary_swamp");
        addFeature(BiomeKeys.MANGROVE_SWAMP, "patch_bog_rosemary_mangrove_swamp");
        addFeature(BiomeKeys.PLAINS, "patch_bulbous_buttercup_plains");
        addFeature(BiomeKeys.SUNFLOWER_PLAINS, "patch_bulbous_buttercup_sunflower_plains");
        addFeature(BiomeKeys.MEADOW, "patch_bulbous_buttercup_meadow");
        addFeature(BiomeKeys.FOREST, "patch_bluebell_forest");
        addFeature(BiomeKeys.FLOWER_FOREST, "patch_bluebell_flower_forest");
        /*?} else {*/
        /*
        addFeature(Biomes.PLAINS, "patch_bells_of_ireland_plains");
        addFeature(Biomes.MEADOW, "patch_bells_of_ireland_meadow");
        addFeature(Biomes.SWAMP, "patch_bog_rosemary_swamp");
        addFeature(Biomes.MANGROVE_SWAMP, "patch_bog_rosemary_mangrove_swamp");
        addFeature(Biomes.PLAINS, "patch_bulbous_buttercup_plains");
        addFeature(Biomes.SUNFLOWER_PLAINS, "patch_bulbous_buttercup_sunflower_plains");
        addFeature(Biomes.MEADOW, "patch_bulbous_buttercup_meadow");
        addFeature(Biomes.FOREST, "patch_bluebell_forest");
        addFeature(Biomes.FLOWER_FOREST, "patch_bluebell_flower_forest");
        */
        /*?}*/

        EmeraldIsleFlora.LOGGER.info("Finished registering World Generation for " + EmeraldIsleFlora.MOD_ID);
        /*?}*/
    }

    // Mapping differences (RegistryKey vs ResourceKey, etc.) are isolated here;
    // register() just passes a biome key and a placed-feature id.

    /*? if fabric && <26.2 {*/
    private static void addFeature(RegistryKey<Biome> biome, String featureId) {
        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(biome),
            GenerationStep.Feature.VEGETAL_DECORATION,
            RegistryKey.of(
                RegistryKeys.PLACED_FEATURE,
                Identifier.of(EmeraldIsleFlora.MOD_ID, featureId))
        );
    }
    /*?}*/

    /*? if fabric && >=26.2 {*/
    /*
    private static void addFeature(ResourceKey<Biome> biome, String featureId) {
        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(biome),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(
                Registries.PLACED_FEATURE,
                Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, featureId))
        );
    }
    */
    /*?}*/
}
