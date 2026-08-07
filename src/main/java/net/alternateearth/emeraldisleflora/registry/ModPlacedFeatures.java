package net.alternateearth.emeraldisleflora.registry;

import java.util.List;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
/*? if <26.2 {*/
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
/*?} else {*/
/*import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
*/
/*?}*/

public class ModPlacedFeatures {
    /*? if <26.2 {*/
    public static final RegistryKey<PlacedFeature> PATCH_BELLS_OF_IRELAND_MEADOW_KEY = registerKey("patch_bells_of_ireland_meadow");
    public static final RegistryKey<PlacedFeature> PATCH_BELLS_OF_IRELAND_PLAINS_KEY = registerKey("patch_bells_of_ireland_plains");

    public static final RegistryKey<PlacedFeature> PATCH_BOG_ROSEMARY_SWAMP_KEY = registerKey("patch_bog_rosemary_swamp");
    public static final RegistryKey<PlacedFeature> PATCH_BOG_ROSEMARY_MANGROVE_SWAMP_KEY = registerKey("patch_bog_rosemary_mangrove_swamp");

    public static final RegistryKey<PlacedFeature> PATCH_BULBOUS_BUTTERCUP_PLAINS_KEY = registerKey("patch_bulbous_buttercup_plains");
    public static final RegistryKey<PlacedFeature> PATCH_BULBOUS_BUTTERCUP_SUNFLOWER_PLAINS_KEY = registerKey("patch_bulbous_buttercup_sunflower_plains");
    public static final RegistryKey<PlacedFeature> PATCH_BULBOUS_BUTTERCUP_MEADOW_KEY = registerKey("patch_bulbous_buttercup_meadow");
    /*?} else {*/
    /*public static final ResourceKey<PlacedFeature> PATCH_BELLS_OF_IRELAND_MEADOW_KEY = registerKey("patch_bells_of_ireland_meadow");
    public static final ResourceKey<PlacedFeature> PATCH_BELLS_OF_IRELAND_PLAINS_KEY = registerKey("patch_bells_of_ireland_plains");

    public static final ResourceKey<PlacedFeature> PATCH_BOG_ROSEMARY_SWAMP_KEY = registerKey("patch_bog_rosemary_swamp");
    public static final ResourceKey<PlacedFeature> PATCH_BOG_ROSEMARY_MANGROVE_SWAMP_KEY = registerKey("patch_bog_rosemary_mangrove_swamp");

    public static final ResourceKey<PlacedFeature> PATCH_BULBOUS_BUTTERCUP_PLAINS_KEY = registerKey("patch_bulbous_buttercup_plains");
    public static final ResourceKey<PlacedFeature> PATCH_BULBOUS_BUTTERCUP_SUNFLOWER_PLAINS_KEY = registerKey("patch_bulbous_buttercup_sunflower_plains");
    public static final ResourceKey<PlacedFeature> PATCH_BULBOUS_BUTTERCUP_MEADOW_KEY = registerKey("patch_bulbous_buttercup_meadow");*/
    /*?}*/

    //--------------------------------------------------------------------------------------------------------------------------------------------------

    /*? if <26.2 {*/
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
    /*?} else {*/
    /*
    // 26.2: same shape, Mojmap names - the old tries=64/xzSpread=6/ySpread=4 that used to
    // live on RandomPatchFeatureConfig (see ModConfiguredFeatures) is now expressed here
    // as two extra placement modifiers appended to the chain: CountPlacement.of(64) for
    // "tries", RandomOffsetPlacement.ofTriangle(6, 4) for "xzSpread, ySpread" - confirmed
    // via javap that ofTriangle(n, m) is exactly of(TrapezoidInt.triangle(n),
    // TrapezoidInt.triangle(m)), and TrapezoidInt.triangle(n) is exactly of(-n, n, 0),
    // i.e. the same +/-spread semantics the old config had. This mirrors vanilla's own
    // real, shipped conversion of an equivalent flower patch (placed_feature/
    // flower_plain.json: count 64, random_offset xz [-6,6] y [-2,2] trapezoid) - same
    // count value (64) as this mod already used, only the xz/y spread differ per-feature
    // as they did before.
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.lookup(Registries.CONFIGURED_FEATURE);

        register(
            context,
            PATCH_BELLS_OF_IRELAND_MEADOW_KEY,
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PATCH_BELLS_OF_IRELAND_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(8),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(64),
                RandomOffsetPlacement.ofTriangle(6, 4)
            )
        );

        register(
            context,
            PATCH_BELLS_OF_IRELAND_PLAINS_KEY,
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PATCH_BELLS_OF_IRELAND_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(32),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(64),
                RandomOffsetPlacement.ofTriangle(6, 4)
            )
        );

        register(
            context,
            PATCH_BOG_ROSEMARY_SWAMP_KEY,
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PATCH_BLENDED_BOG_ROSEMARY_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(40),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(64),
                RandomOffsetPlacement.ofTriangle(6, 4)
            )
        );

        register(
            context,
            PATCH_BOG_ROSEMARY_MANGROVE_SWAMP_KEY,
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PATCH_BOG_ROSEMARY_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(8),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(64),
                RandomOffsetPlacement.ofTriangle(6, 4)
            )
        );

        register(
            context,
            PATCH_BULBOUS_BUTTERCUP_PLAINS_KEY,
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PATCH_BLENDED_BULBOUS_BUTTERCUP_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(16),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(64),
                RandomOffsetPlacement.ofTriangle(6, 4)
            )
        );

        register(
            context,
            PATCH_BULBOUS_BUTTERCUP_SUNFLOWER_PLAINS_KEY,
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PATCH_BLENDED_BULBOUS_BUTTERCUP_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(16),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(64),
                RandomOffsetPlacement.ofTriangle(6, 4)
            )
        );

        register(
            context,
            PATCH_BULBOUS_BUTTERCUP_MEADOW_KEY,
            configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PATCH_BLENDED_BULBOUS_BUTTERCUP_KEY),
            List.of(
                RarityFilter.onAverageOnceEvery(24),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome(),
                CountPlacement.of(64),
                RandomOffsetPlacement.ofTriangle(6, 4)
            )
        );
    }
    */
    /*?}*/

    //--------------------------------------------------------------------------------------------------------------------------------------------------

    /*? if <26.2 {*/
    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(EmeraldIsleFlora.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
    /*?} else {*/
    /*public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(EmeraldIsleFlora.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }*/
    /*?}*/
}
