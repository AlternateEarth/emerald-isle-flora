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

    // Everything that differs between the two mapping sets - RegistryKey vs ResourceKey,
    // RegistryKeys vs Registries, Identifier.of vs Identifier.fromNamespaceAndPath,
    // GenerationStep.Feature vs GenerationStep.Decoration - lives only here, confirmed
    // via javap against the real jars for both mapping sets. register() just passes a
    // biome key and a placed-feature id.

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
