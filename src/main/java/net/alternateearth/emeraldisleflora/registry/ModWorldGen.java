package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public final class ModWorldGen {

    public static void register() {
        EmeraldIsleFlora.LOGGER.info("Registering World Generation for " + EmeraldIsleFlora.MOD_ID);

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

        EmeraldIsleFlora.LOGGER.info("Finished registering World Generation for " + EmeraldIsleFlora.MOD_ID);
    }
}
