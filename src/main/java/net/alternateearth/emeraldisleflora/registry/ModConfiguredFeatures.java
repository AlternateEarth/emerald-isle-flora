package net.alternateearth.emeraldisleflora.registry;

import net.alternateearth.emeraldisleflora.EmeraldIsleFlora;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BELLS_OF_IRELAND_KEY = registerKey("patch_bells_of_ireland");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_BOG_ROSEMARY_KEY = registerKey("path_bog_rosemary");

    //--------------------------------------------------------------------------------------------------------------------------------------------------

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(
            context, 
            PATCH_BELLS_OF_IRELAND_KEY, 
            Feature.FLOWER, 
            new RandomPatchFeatureConfig(
                64, 
                6, 
                4, 
                PlacedFeatures.createEntry(
                    Feature.SIMPLE_BLOCK, 
                    new SimpleBlockFeatureConfig(
                        BlockStateProvider.of(ModBlocks.BELLS_OF_IRELAND)
                    )
                ))
        );

        register(
            context,
            PATCH_BOG_ROSEMARY_KEY,
            Feature.FLOWER,
            new RandomPatchFeatureConfig(
                64,
                6,
                4,
                PlacedFeatures.createEntry(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(
                        BlockStateProvider.of(ModBlocks.BOG_ROSEMARY)
                    )
                ))
        );
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(EmeraldIsleFlora.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
