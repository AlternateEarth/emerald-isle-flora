package net.alternateearth.emeraldisleflora.registry;

import java.util.List;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> PATCH_BELLS_OF_IRELAND_MEADOW_KEY = registerKey("patch_bells_of_ireland_meadow");
    public static final RegistryKey<PlacedFeature> PATCH_BELLS_OF_IRELAND_PLAINS_KEY = registerKey("patch_bells_of_ireland_plains");

    public static final RegistryKey<PlacedFeature> PATCH_BOG_ROSEMARY_SWAMP_KEY = registerKey("patch_bog_rosemary_swamp");
    public static final RegistryKey<PlacedFeature> PATCH_BOG_ROSEMARY_MANGROVE_SWAMP_KEY = registerKey("patch_bog_rosemary_mangrove_swamp");

    public static final RegistryKey<PlacedFeature> PATCH_BULBOUS_BUTTERCUP_PLAINS_KEY = registerKey("patch_bulbous_buttercup_plains");
    public static final RegistryKey<PlacedFeature> PATCH_BULBOUS_BUTTERCUP_SUNFLOWER_PLAINS_KEY = registerKey("patch_bulbous_buttercup_sunflower_plains");
    public static final RegistryKey<PlacedFeature> PATCH_BULBOUS_BUTTERCUP_MEADOW_KEY = registerKey("patch_bulbous_buttercup_meadow");

    //--------------------------------------------------------------------------------------------------------------------------------------------------

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(
            context, 
            PATCH_BELLS_OF_IRELAND_MEADOW_KEY, 
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PATCH_BELLS_OF_IRELAND_KEY), 
            List.of(
                RarityFilterPlacementModifier.of(8),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()
            )
        );

        register(
            context, 
            PATCH_BELLS_OF_IRELAND_PLAINS_KEY, 
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PATCH_BELLS_OF_IRELAND_KEY), 
            List.of(
                RarityFilterPlacementModifier.of(32),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()
            )
        );

        register(
            context,
            PATCH_BOG_ROSEMARY_SWAMP_KEY,
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PATCH_BLENDED_BOG_ROSEMARY_KEY),
            List.of(
                RarityFilterPlacementModifier.of(40),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()
            )
        );

        register(
            context,
            PATCH_BOG_ROSEMARY_MANGROVE_SWAMP_KEY,
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PATCH_BOG_ROSEMARY_KEY),
            List.of(
                RarityFilterPlacementModifier.of(8),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()
            )
        );

        register(
            context,
            PATCH_BULBOUS_BUTTERCUP_PLAINS_KEY,
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PATCH_BLENDED_BULBOUS_BUTTERCUP_KEY),
            List.of(
                RarityFilterPlacementModifier.of(16),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()
            )
        );

        register(
            context,
            PATCH_BULBOUS_BUTTERCUP_SUNFLOWER_PLAINS_KEY,
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PATCH_BLENDED_BULBOUS_BUTTERCUP_KEY),
            List.of(
                RarityFilterPlacementModifier.of(16),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()
            )
        );

        register(
            context,
            PATCH_BULBOUS_BUTTERCUP_MEADOW_KEY,
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PATCH_BLENDED_BULBOUS_BUTTERCUP_KEY),
            List.of(
                RarityFilterPlacementModifier.of(24),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()
            )
        );
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(EmeraldIsleFlora.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
